package com.eazybytes.springsecsection10.repository;

import com.eazybytes.springsecsection10.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {

    List<Customer> findByEmail(String email);
}
