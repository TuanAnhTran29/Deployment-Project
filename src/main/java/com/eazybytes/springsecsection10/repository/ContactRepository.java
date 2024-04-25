package com.eazybytes.springsecsection10.repository;

import com.eazybytes.springsecsection10.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
}
