package com.stevecode;

import com.stevecode.customer.Customer;
import com.stevecode.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication

public class Main {






    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(Main.class, args);

    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {

        return args -> {
            Customer peter = new Customer(
                    "Peter",
                    "peter@mail.com",
                    22
            );
            Customer sugwinda = new Customer(
                    "Sugwinda",
                    "jamol@mail.com",
                    19
            );

            List<Customer> customers = List.of(peter, sugwinda);
            customerRepository.saveAll(customers);
        };
    }


    //Foo is outside of the Application-Context -> Not management for us
    //Bean -> in Application Context
    @Bean("foo")
    public Foo getFoo() {
        //Do mutch logic before instance from spring
        return new Foo("SuperFoo");
    }

    record Foo(String name){}



}
