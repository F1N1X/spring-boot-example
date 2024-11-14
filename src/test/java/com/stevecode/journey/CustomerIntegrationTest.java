package com.stevecode.journey;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.stevecode.customer.Customer;
import com.stevecode.customer.CustomerRegistrationRequest;
import com.stevecode.customer.CustomerUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


//Doc : https://docs.spring.io/spring-boot/reference/testing/index.html#testing

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final Random RANDOM = new Random();

    //Never user direct API use like Postmen - Request

    private static final String CUSTOMER_URI = "/api/v1/customers";

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
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        // get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        Customer expected = new Customer(
                name, email, age
        );

        // make sure that customer is present
        assertThat(allCustomers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expected);

        // get customer by id
        var id = allCustomers.stream()
                        .filter(customer -> customer.getEmail().equals(email))
                        .map(Customer::getId)
                        .findFirst()
                         .orElseThrow();

        expected.setId(id);

        webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {})
                .isEqualTo(expected);
    }

    @Test
    void canDeleteCustomer() {
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
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        // get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();



        // get customer by id
        var id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        //str + alt + l
        webTestClient.delete()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();


        webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
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
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        // get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        Customer customerChangeModel = new Customer(
                name, email, age
        );

        // make sure that customer is present
        assertThat(allCustomers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(customerChangeModel);

        // get customer by id
        var id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        customerChangeModel.setId(id);



        var updateRequest = new CustomerUpdateRequest(
                faker.name().fullName(),
                faker.internet().emailAddress(),
                faker.number().numberBetween(1,30)
        );


        webTestClient.put()
                .uri(CUSTOMER_URI + "/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();




        // get all customers
        Customer actualCustomer = webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

       assertThat(actualCustomer).isNotEqualTo(customerChangeModel);

    }
}
