package com.stevecode.customer;

import com.stevecode.exception.DuplicateResourceException;
import com.stevecode.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

//Service for BL
@Service
public class CustomerService {

    private final CustomerDao customerDao;


    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException(
                                "Customer with id [%s] not found".formatted(id)
                        )
                );
    }

    public void addCustomer(
            CustomerRegistrationRequest customerRegistrationRequest) {


        if (customerDao.existsPersonWithEmail(customerRegistrationRequest.email())) {
            throw new DuplicateResourceException("Email already taken");
        }

        customerDao.insertCustomer(
                new Customer(
                        customerRegistrationRequest.name(),
                        customerRegistrationRequest.email(),
                        customerRegistrationRequest.age()
                )
        );

    }
    public void deleteCustomerById(Integer customerId) {
        if (!customerDao.existPersonWithId(customerId))
            throw new ResourceNotFoundException(
                    "Customer with ID [%s] not exist".formatted(customerId)
            );
        customerDao.deleteCustomerById(customerId);
    }
}
