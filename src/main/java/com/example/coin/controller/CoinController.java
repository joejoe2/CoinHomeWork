package com.example.coin.controller;

import com.example.coin.service.CoinService;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/api/coin")
public class CoinController {
  private final CoinService coinService;

  public CoinController(CoinService coinService) {
    this.coinService = coinService;
  }

  @RequestMapping(path = "/coindesk/bpi", method = RequestMethod.GET)
  public ResponseEntity<Object> getBPI() {
    try {
      return ResponseEntity.ok(coinService.getBPI());
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }
  }
}
