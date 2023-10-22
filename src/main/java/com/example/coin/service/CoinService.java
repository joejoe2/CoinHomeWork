package com.example.coin.service;

import com.example.coin.data.Bpi;
import java.io.IOException;

public interface CoinService {
  Bpi getBPI() throws IOException;

  String getFromCoinDeskApi() throws IOException;
}
