package com.example.coin.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.coin.data.Bpi;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CoinServiceTest {
  @SpyBean CoinService coinService;
  Map<String, Float> rates;

  Logger logger = LoggerFactory.getLogger(CoinServiceTest.class);

  @BeforeEach
  void setUp() {
    rates = new HashMap<>();
    rates.put("USD", 28257.7922f);
    rates.put("GBP", 23611.9851f);
    rates.put("EUR", 27527.2152f);
  }

  static String bpiSample(String updatedISO, Map<String, Float> rates) {
    StringBuilder sample =
        new StringBuilder(
            String.format(
                "{\"time\":{\"updated\":\"Oct 19, 2023 04:07:00"
                    + " UTC\",\"updatedISO\":\"%s\",\"updateduk\":\"Oct 19, 2023 at 05:07"
                    + " BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin"
                    + " Price Index (USD). Non-USD currency data converted using hourly conversion"
                    + " rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{",
                updatedISO));
    String template =
        "\"%s\":{\"code\":\"%s\",\"symbol\":\"&#36;\",\"rate\":\"28,257.7922\",\"description\":\"random\",\"rate_float\":%f}";
    int i = 0;
    for (String code : rates.keySet()) {
      sample.append(String.format(template, code, code, rates.get(code)));
      if (i != rates.size() - 1) sample.append(",");
      i++;
    }
    sample.append("}}");
    return sample.toString();
  }

  @Test
  void getBPI() throws Exception {
    Map<String, Float> data = new HashMap<>(rates);
    data.put("not exist", 123.0f);
    String sample = bpiSample("2023-10-19T04:07:00+00:00", data);
    Mockito.doReturn(sample).when(coinService).getFromCoinDeskApi();
    Bpi bpi = coinService.getBPI();
    assertEquals(Instant.parse("2023-10-19T04:07:00+00:00"), bpi.getUpdatedAt());
    assertEquals(rates.size(), bpi.getIndices().size());
  }

  // case 5.
  @Test
  void getFromCoinDeskApi() throws Exception {
    logger.info("Case 5:\n" + coinService.getFromCoinDeskApi());
  }
}
