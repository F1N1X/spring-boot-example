package com.stevecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
public class Main {



    //db
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


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
/*
    //use v1 for backend comp.
    @RequestMapping(
            path = "api/v1/customer",
            method = RequestMethod.GET
    )
    //Longer Version-> GetMapping
    */
    @GetMapping("api/v1/customers")
    public List<Customer> getCustomers() {
        return customers;
    }

    @GetMapping("api/v1/customers/{customerId}")
    public Customer getCustomer(
            @PathVariable("customerId") Integer customerId) {
        return customers.stream()
                .filter(customer -> customer.id.equals(customerId))
                .findFirst()
                .orElseThrow(() ->new IllegalArgumentException("customer with id [%s] not found".formatted(customerId)));
    }

    //alt+einf
    static class Customer {
        private Integer id;
        private String name;
        private String email;
        private Integer age;

        public  Customer() {}

        public Customer(Integer id, String name, String email, Integer age) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Customer customer = (Customer) o;
            return id == customer.id && age == customer.age && Objects.equals(name, customer.name) && Objects.equals(email, customer.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, email, age);
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
