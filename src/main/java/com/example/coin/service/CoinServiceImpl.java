package com.example.coin.service;

import com.example.coin.config.CoinDeskConfig;
import com.example.coin.data.Bpi;
import com.example.coin.data.Index;
import com.example.coin.data.RawBPI;
import com.example.coin.data.RawIndex;
import com.example.coin.model.Currency;
import com.example.coin.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CoinServiceImpl implements CoinService {
  private final CoinDeskConfig coinDeskConfig;
  private final ObjectMapper objectMapper;
  private final CurrencyRepository currencyRepository;

  public CoinServiceImpl(
      CoinDeskConfig coinDeskConfig,
      ObjectMapper objectMapper,
      CurrencyRepository currencyRepository) {
    this.coinDeskConfig = coinDeskConfig;
    this.objectMapper = objectMapper;
    this.currencyRepository = currencyRepository;
  }

  @Override
  public Bpi getBPI() throws IOException {
    RawBPI rawBPI = objectMapper.readValue(getFromCoinDeskApi(), RawBPI.class);
    Bpi bpi = new Bpi();
    bpi.setUpdatedAt(rawBPI.getUpdatedAt());
    Map<String, Currency> currencies =
        currencyRepository
            .findCurrencyByCodeIn(
                rawBPI.getIndices().stream().map(RawIndex::getCode).collect(Collectors.toSet()))
            .stream()
            .collect(Collectors.toMap(Currency::getCode, v -> v));
    bpi.setIndices(new ArrayList<>());
    for (RawIndex rawIndex : rawBPI.getIndices()) {
      Currency currency = currencies.get(rawIndex.getCode());
      if (currency != null) {
        Index index =
            new Index(currency.getCode(), currency.getChineseAlias(), rawIndex.getRateFloat());
        bpi.getIndices().add(index);
      }
    }
    return bpi;
  }

  @Override
  public String getFromCoinDeskApi() throws IOException {
    try (InputStream input = new URL(coinDeskConfig.getBpiUrl()).openStream()) {
      InputStreamReader isr = new InputStreamReader(input);
      BufferedReader reader = new BufferedReader(isr);
      StringBuilder json = new StringBuilder();
      int c;
      while ((c = reader.read()) != -1) {
        json.append((char) c);
      }
      return json.toString();
    }
  }
}
