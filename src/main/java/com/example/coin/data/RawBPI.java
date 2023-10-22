package com.example.coin.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonDeserialize(using = RawBpiDeserializer.class)
public class RawBPI {
  private Instant updatedAt;

  private List<RawIndex> indices;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RawBPI)) return false;
    RawBPI rawBpi = (RawBPI) o;
    return updatedAt.equals(rawBpi.updatedAt) && indices.equals(rawBpi.indices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(updatedAt, indices);
  }
}
