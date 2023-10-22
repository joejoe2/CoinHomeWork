package com.example.coin.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.coin.data.CurrencyInfoDto;
import com.example.coin.data.CurrencyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CurrencyControllerTest {
  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  Logger logger = LoggerFactory.getLogger(CurrencyControllerTest.class);

  // case 1-1
  @Test
  void get() throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/currency/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    logger.info("Case 1-1:\n" + result.getResponse().getContentAsString(StandardCharsets.UTF_8));
  }

  // case 1-2
  @Test
  void list() throws Exception {
    MvcResult result =
        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/currency").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    logger.info("Case 1-2:\n" + result.getResponse().getContentAsString(StandardCharsets.UTF_8));
  }

  // case 2
  @Test
  @Transactional
  void create() throws Exception {
    CurrencyRequest request = CurrencyRequest.builder().code("TEST").chineseAlias("測試幣").build();
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/currency")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    CurrencyInfoDto currencyInfoDto =
        objectMapper.readValue(
            result.getResponse().getContentAsString(StandardCharsets.UTF_8), CurrencyInfoDto.class);
    assertNotNull(currencyInfoDto.getId());
    assertEquals(request.getCode(), currencyInfoDto.getCode());
    assertEquals(request.getChineseAlias(), currencyInfoDto.getChineseAlias());
  }

  // case 3
  @Test
  @Transactional
  void update() throws Exception {
    // prepare
    CurrencyRequest request = CurrencyRequest.builder().code("TEST").chineseAlias("測試幣").build();
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/currency")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    CurrencyInfoDto currencyInfoDto =
        objectMapper.readValue(
            result.getResponse().getContentAsString(StandardCharsets.UTF_8), CurrencyInfoDto.class);
    // test update
    request = CurrencyRequest.builder().code("TEST_UPDATE").chineseAlias("更新測試幣").build();
    result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/api/currency/" + currencyInfoDto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    CurrencyInfoDto updatedCurrencyInfoDto =
        objectMapper.readValue(
            result.getResponse().getContentAsString(StandardCharsets.UTF_8), CurrencyInfoDto.class);
    assertEquals(currencyInfoDto.getId(), updatedCurrencyInfoDto.getId());
    assertEquals(request.getCode(), updatedCurrencyInfoDto.getCode());
    assertEquals(request.getChineseAlias(), updatedCurrencyInfoDto.getChineseAlias());

    logger.info("Case 3:\n" + result.getResponse().getContentAsString(StandardCharsets.UTF_8));
  }

  // case 4
  @Test
  @Transactional
  void delete() throws Exception {
    // prepare
    CurrencyRequest request = CurrencyRequest.builder().code("TEST").chineseAlias("測試幣").build();
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/currency")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    CurrencyInfoDto currencyInfoDto =
        objectMapper.readValue(
            result.getResponse().getContentAsString(StandardCharsets.UTF_8), CurrencyInfoDto.class);
    // test delete
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/api/currency/" + currencyInfoDto.getId()))
        .andExpect(status().isOk())
        .andReturn();
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/currency/" + currencyInfoDto.getId())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();
  }
}
