package com.example.coin.controller;

import com.example.coin.data.CurrencyInfoDto;
import com.example.coin.data.CurrencyRequest;
import com.example.coin.exception.AlreadyExist;
import com.example.coin.exception.DoesNotExist;
import com.example.coin.repository.CurrencyRepository;
import com.example.coin.service.CurrencyService;
import com.sun.istack.NotNull;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/api/currency")
public class CurrencyController {
  private final CurrencyService currencyService;
  private final CurrencyRepository currencyRepository;

  public CurrencyController(
      CurrencyService currencyService, CurrencyRepository currencyRepository) {
    this.currencyService = currencyService;
    this.currencyRepository = currencyRepository;
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Object> get(@Validated @NotNull @PathVariable("id") int id) {
    try {
      return ResponseEntity.ok(
          new CurrencyInfoDto(
              currencyRepository
                  .findById(id)
                  .orElseThrow(() -> new DoesNotExist("currency with id=%d does not exist !"))));
    } catch (DoesNotExist e) {
      return ResponseEntity.notFound().build();
    }
  }

  @RequestMapping(path = "", method = RequestMethod.GET)
  public ResponseEntity<Object> getAll() {
    return ResponseEntity.ok(
        currencyRepository.findAllOrderById().stream()
            .map(CurrencyInfoDto::new)
            .collect(Collectors.toList()));
  }

  @RequestMapping(path = "", method = RequestMethod.POST)
  public ResponseEntity<Object> create(@Valid @RequestBody CurrencyRequest request) {
    try {
      return ResponseEntity.ok(
          currencyService.create(request.getCode(), request.getChineseAlias()));
    } catch (AlreadyExist e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Object> update(
      @Validated @NotNull @PathVariable("id") int id, @Valid @RequestBody CurrencyRequest request) {
    try {
      return ResponseEntity.ok(
          currencyService.update(id, request.getCode(), request.getChineseAlias()));
    } catch (DoesNotExist e) {
      return ResponseEntity.notFound().build();
    } catch (AlreadyExist e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Object> delete(@Validated @NotNull @PathVariable("id") int id) {
    try {
      currencyService.delete(id);
    } catch (DoesNotExist e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }
}
