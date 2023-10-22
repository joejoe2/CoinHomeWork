package com.example.coin.service;

import com.example.coin.data.CurrencyInfoDto;
import com.example.coin.exception.AlreadyExist;
import com.example.coin.exception.DoesNotExist;
import com.example.coin.model.Currency;
import com.example.coin.repository.CurrencyRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrencyServiceImpl implements CurrencyService {
  private final CurrencyRepository currencyRepository;

  public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
    this.currencyRepository = currencyRepository;
  }

  @Override
  @Transactional
  public CurrencyInfoDto create(String code, String chineseAlias) throws AlreadyExist {
    if (currencyRepository.findByCode(code).isPresent())
      throw new AlreadyExist(String.format("currency code=%s cannot be duplicated !", code));
    Currency currency = new Currency(code, chineseAlias);
    currencyRepository.saveAndFlush(currency);
    return new CurrencyInfoDto(currency);
  }

  @Override
  @Transactional
  public CurrencyInfoDto update(int id, String code, String chineseAlias)
      throws DoesNotExist, AlreadyExist {
    Currency currency =
        currencyRepository
            .findById(id)
            .orElseThrow(
                () -> new DoesNotExist(String.format("currency with id=%d does not exist !", id)));
    Optional<Currency> currencyWithSameCode = currencyRepository.findByCode(code);
    if (currencyWithSameCode.isPresent() && currencyWithSameCode.get().getId() != id) {
      throw new AlreadyExist(String.format("currency with code=%s cannot be duplicated !", code));
    }

    currency.setCode(code);
    currency.setChineseAlias(chineseAlias);
    currencyRepository.save(currency);
    return new CurrencyInfoDto(currency);
  }

  @Override
  @Transactional
  public void delete(int id) throws DoesNotExist {
    Currency currency =
        currencyRepository
            .findById(id)
            .orElseThrow(
                () -> new DoesNotExist(String.format("currency with id=%d does not exist !", id)));
    currencyRepository.delete(currency);
  }
}
