package com.stevecode.customer;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository
        extends JpaRepository<Customer,Integer> {

    //@Query("select c where name...")

    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Integer id);

//Refactor Test -> JUnit5 -> setUP/@Before


}
