package com.stevecode.customer;

import java.util.List;
import java.util.Optional;


//Reimplement with RL-Project
public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);

    void insertCustomer(Customer customer);
    boolean existsPersonWithEmail(String email);

    boolean existPersonWithId(Integer id);

    void deleteCustomerById(Integer id);

    void updateCustomer(Customer updateCustomer);

    boolean checkUpdateData(Customer updateCustomer);

}
