package com.example.coin.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.coin.data.CurrencyInfoDto;
import com.example.coin.exception.AlreadyExist;
import com.example.coin.exception.DoesNotExist;
import com.example.coin.model.Currency;
import com.example.coin.repository.CurrencyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
class CurrencyServiceTest {
  @Autowired CurrencyService currencyService;
  @Autowired CurrencyRepository currencyRepository;
  Currency currency;

  @BeforeEach
  void setUp() {
    currencyRepository.findAll();
  }

  @AfterEach
  void tearDown() {}

  @Test
  @Transactional
  void create() throws Exception {
    CurrencyInfoDto currencyInfoDto = currencyService.create("TEST", "測試幣");
    assertNotNull(currencyInfoDto.getId());
    assertEquals("TEST", currencyInfoDto.getCode());
    assertEquals("測試幣", currencyInfoDto.getChineseAlias());
  }

  @Test
  void createWithAlreadyExist() {
    assertThrows(AlreadyExist.class, () -> currencyService.create("USD", "美元"));
  }

  @Test
  @Transactional
  void update() throws Exception {
    CurrencyInfoDto currencyInfoDto = currencyService.create("TEST", "測試幣");
    CurrencyInfoDto updatedCurrencyInfoDto =
        currencyService.update(currencyInfoDto.getId(), "TEST_UPDATE", "測試更新幣");
    assertEquals(currencyInfoDto.getId(), updatedCurrencyInfoDto.getId());
    assertEquals("TEST_UPDATE", updatedCurrencyInfoDto.getCode());
    assertEquals("測試更新幣", updatedCurrencyInfoDto.getChineseAlias());
  }

  @Test
  void updateWithDoesNotExist() {
    // given id does not exist
    assertThrows(DoesNotExist.class, () -> currencyService.update(65535, "NOT_EXIST", "不存在幣"));
  }

  @Test
  @Transactional
  void updateWithAlreadyExist() throws Exception {
    CurrencyInfoDto currencyInfoDto = currencyService.create("TEST", "測試幣");
    // code "USD" has been created
    assertThrows(
        AlreadyExist.class, () -> currencyService.update(currencyInfoDto.getId(), "USD", "存在幣"));
  }

  @Test
  @Transactional
  void delete() throws Exception {
    CurrencyInfoDto currencyInfoDto = currencyService.create("TEST", "測試幣");
    currencyService.delete(currencyInfoDto.getId());
    assertFalse(currencyRepository.existsById(currencyInfoDto.getId()));
  }

  @Test
  void deleteWithDoesNotExist() {
    // given id does not exist
    assertThrows(DoesNotExist.class, () -> currencyService.delete(65535));
  }
}
