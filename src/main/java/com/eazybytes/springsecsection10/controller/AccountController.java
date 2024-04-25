package com.eazybytes.springsecsection10.controller;

import com.eazybytes.springsecsection10.model.Accounts;
import com.eazybytes.springsecsection10.model.Customer;
import com.eazybytes.springsecsection10.repository.AccountsRepository;
import com.eazybytes.springsecsection10.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email){
        List<Customer> customers= customerRepository.findByEmail(email);
        if (customers!= null && !customers.isEmpty()){
            Accounts accounts= repository.findByCustomerId(customers.get(0).getId());
            if (accounts!= null){
                return accounts;
            }
        }
        return null;
    }
}
