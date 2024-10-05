package com.stevecode.customer;

import java.util.List;
import java.util.Optional;


//Reimplement with RL-Project
public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);
}
