package com.example.coin.model;

import java.util.Objects;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(
    name = "currency_info",
    indexes = {
      @Index(columnList = "code"),
    })
public class Currency {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String code;

  @Column(nullable = false)
  private String chineseAlias;

  public Currency(String code, String chineseAlias) {
    if (code == null || chineseAlias == null || code.length() == 0 || chineseAlias.length() == 0)
      throw new IllegalArgumentException();
    this.code = code;
    this.chineseAlias = chineseAlias;
  }

  public void setCode(String code) {
    if (code == null || code.length() == 0) throw new IllegalArgumentException();
    this.code = code;
  }

  public void setChineseAlias(String chineseAlias) {
    if (chineseAlias == null || chineseAlias.length() == 0) throw new IllegalArgumentException();
    this.chineseAlias = chineseAlias;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Currency)) return false;
    Currency currency = (Currency) o;
    return id.equals(currency.id)
        && code.equals(currency.code)
        && chineseAlias.equals(currency.chineseAlias);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, chineseAlias);
  }
}
