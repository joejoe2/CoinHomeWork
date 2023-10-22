package com.example.coin.data;

import com.example.coin.model.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CurrencyInfoDto {
  private Integer id;
  private String code;
  private String chineseAlias;

  public CurrencyInfoDto(Currency currency) {
    this.id = currency.getId();
    this.code = currency.getCode();
    this.chineseAlias = currency.getChineseAlias();
  }
}
