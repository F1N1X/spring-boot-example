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
                1,
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


    //alt+einf
    static class Customer {
        private int id;
        private String name;
        private String email;
        private int age;

        public  Customer() {}

        public Customer(int id, String name, String email, int age) {
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
