package com.stevecode.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDataAccessService  implements CustomerDao {


    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer peter = new Customer(
                1,
                "Peter",
                "peter@mail.com",
                22
        );
        Customer jasmin = new Customer(
                2,
                "Jasmin",
                "jamol@mail.com",
                19
        );
        customers.add(peter);
        customers.add(jasmin);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }
}


