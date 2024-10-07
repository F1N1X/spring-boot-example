package com.stevecode.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JPADataAccessService implements CustomerDao{

    private final CustomerRepository customerRepository;

    public JPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.empty();
    }
}
