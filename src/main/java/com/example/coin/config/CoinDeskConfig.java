package com.example.coin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class CoinDeskConfig {
  @Value("${coin-desk.api.bpi}")
  private String bpiUrl;
}
