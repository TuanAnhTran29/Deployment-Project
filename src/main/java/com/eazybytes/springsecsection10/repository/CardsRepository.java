package com.eazybytes.springsecsection10.repository;

import com.eazybytes.springsecsection10.model.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {
    List<Cards> findByCustomerId(int customerId);
}
