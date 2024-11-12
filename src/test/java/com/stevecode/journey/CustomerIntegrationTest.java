package com.stevecode.journey;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.stevecode.customer.CustomerRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


//Doc : https://docs.spring.io/spring-boot/reference/testing/index.html#testing

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final Random RANDOM = new Random();

    //Never user direct API use like Postmen - Request

    @Test
    void canRegisterCustomer() {
        // create customer registration
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = name + UUID.randomUUID()+"@stevecode.com";
        int age = RANDOM.nextInt(1,100);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name, email, age
        );
        // send a post request
        webTestClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        // get all customers
        // make sure that customer is present
        // get customer by id

    }
}
