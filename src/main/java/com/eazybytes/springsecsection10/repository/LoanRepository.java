package com.eazybytes.springsecsection10.repository;

import com.eazybytes.springsecsection10.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {

    @PreAuthorize("hasRole('USER')")
    List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);
}
