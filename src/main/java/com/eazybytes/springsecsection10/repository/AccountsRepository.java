package com.eazybytes.springsecsection10.repository;

import com.eazybytes.springsecsection10.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {
    Accounts findByCustomerId(int customerId);
}
