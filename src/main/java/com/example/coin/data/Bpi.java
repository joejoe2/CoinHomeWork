package com.example.coin.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Bpi {
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "UTC")
  private Instant updatedAt;

  private List<Index> indices;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Bpi)) return false;
    Bpi bpi = (Bpi) o;
    return updatedAt.equals(bpi.updatedAt) && indices.equals(bpi.indices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(updatedAt, indices);
  }
}
