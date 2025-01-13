package com.stevecode;

import com.github.javafaker.Faker;
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

            Faker faker = new Faker();

            Customer testFaker = new Customer(
                    faker.name().fullName(),
                    faker.internet().safeEmailAddress(),
                    faker.number().numberBetween(10,50)
            );




            //List<Customer> customers = List.of(peter, sugwinda);
            customerRepository.save(testFaker);
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
