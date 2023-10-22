package com.example.coin.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RawIndex {
  private String code;

  private String symbol;

  private String description;

  private String rate;

  @JsonProperty("rate_float")
  Float rateFloat;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RawIndex)) return false;
    RawIndex index = (RawIndex) o;
    return code.equals(index.code)
        && symbol.equals(index.symbol)
        && description.equals(index.description)
        && rate.equals(index.rate)
        && rateFloat.equals(index.rateFloat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, symbol, description, rate, rateFloat);
  }
}
