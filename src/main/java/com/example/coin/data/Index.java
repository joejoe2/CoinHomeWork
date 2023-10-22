package com.example.coin.data;

import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Index {
  private String currency;
  private String chineseAlias;
  private Float rate;

  public Index(String currency, String chineseAlias, Float rate) {
    this.currency = currency;
    this.chineseAlias = chineseAlias;
    this.rate = rate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Index)) return false;
    Index index = (Index) o;
    return currency.equals(index.currency)
        && chineseAlias.equals(index.chineseAlias)
        && rate.equals(index.rate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency, chineseAlias, rate);
  }
}
