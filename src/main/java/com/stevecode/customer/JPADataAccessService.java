package com.stevecode.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository("jpa")
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
        return customerRepository.findById(id);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean existPersonWithId(Integer id) {
       return customerRepository.existsById(id);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Customer updateCustomer) {
        var customerFromDb = customerRepository.getReferenceById(updateCustomer.getId());

        if (updateCustomer.getEmail() != null) {
            customerFromDb.setEmail(updateCustomer.getEmail());
        }
        if (updateCustomer.getAge() != null) {
            customerFromDb.setAge(updateCustomer.getAge());
        }
        if (updateCustomer.getName() != null) {
            customerFromDb.setName(updateCustomer.getName());
        }

        customerRepository.save(customerFromDb);
    }

}
