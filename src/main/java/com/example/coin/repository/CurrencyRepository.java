package com.example.coin.repository;

import com.example.coin.model.Currency;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
  List<Currency> findCurrencyByCodeIn(Set<String> code);

  Optional<Currency> findByCode(String code);

  @Query("SELECT c from Currency c order by c.id asc")
  List<Currency> findAllOrderById();
}
