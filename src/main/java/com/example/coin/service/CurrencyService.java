package com.example.coin.service;

import com.example.coin.data.CurrencyInfoDto;
import com.example.coin.exception.AlreadyExist;
import com.example.coin.exception.DoesNotExist;

public interface CurrencyService {
  CurrencyInfoDto create(String code, String chineseAlias) throws AlreadyExist;

  CurrencyInfoDto update(int id, String code, String chineseAlias)
      throws DoesNotExist, AlreadyExist;

  void delete(int id) throws DoesNotExist;
}
