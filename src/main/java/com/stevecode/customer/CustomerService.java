package com.stevecode.customer;

import com.stevecode.exception.DuplicateResourceException;
import com.stevecode.exception.RequestValidationException;
import com.stevecode.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

//Service for BL
@Service
public class CustomerService {

    private final CustomerDao customerDao;


    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
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

    public void updateCustomer(Integer customerId,CustomerUpdateRequest customerUpdateRequest) {
        if (!customerDao.existPersonWithId(customerId))
            throw new ResourceNotFoundException(
                    "Customer with ID [%s] not exist".formatted(customerId)
            );



        var changeFound = false;
        Customer customer = getCustomer(customerId);

        if (!(customer.getAge().equals(customerUpdateRequest.age())) && customerUpdateRequest.age() != null) {
            customer.setAge(customerUpdateRequest.age());
            changeFound = true;
        }

        if (!(customer.getName().equals(customerUpdateRequest.name())) && customerUpdateRequest.name() != null) {
            customer.setName(customerUpdateRequest.name());
            changeFound = true;
        }

        if (!(customer.getEmail().equals(customerUpdateRequest.email())) && customerUpdateRequest.email() != null) {
            if (customerDao.existsPersonWithEmail(customerUpdateRequest.email()))
                throw new DuplicateResourceException(
                        "Email [%s] is already taken".formatted(customerUpdateRequest.email()));

            customer.setEmail(customerUpdateRequest.email());
            changeFound = true;
        }


        if (!changeFound)
            throw new RequestValidationException(
                    "Customer with ID [%s] nothing to update".formatted(customerId)
            );

        customerDao.updateCustomer(customer);

    }
}
