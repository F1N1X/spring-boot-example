package com.stevecode.customer;

import com.stevecode.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }
    @Test
    void existsCustomerByEmail() {
        //Given
        var mail = FAKER.internet().emailAddress();
        var customer = new Customer(
                FAKER.name().fullName(),
                mail,
                FAKER.number().numberBetween(16, 60)
        );
        underTest.save(customer);

        var isIdFound = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(mail))
                .findFirst()
                .map(Customer::getEmail)
                .map(underTest::existsCustomerByEmail)
                .orElseThrow();


        //When
        assertThat(isIdFound).isTrue();
    }

    @Test
    void existsCustomerByEmailFailsWhenNotExists() {
        //Given
       String email = FAKER.internet().emailAddress()+"-"+ UUID.randomUUID();

       //When
        var acutal = underTest.existsCustomerByEmail(email);

        //Then
        assertThat(acutal).isFalse();

    }


    @Test
    void existsCustomerByIdFailsWhenNotExists() {
        //Given
        var id = -1;


        //When
        var acutal = underTest.existsCustomerById(id);

        //Then
        assertThat(acutal).isFalse();

    }

    @Test
    void existsCustomerById() {
        //Given
        var mail = FAKER.internet().emailAddress();
        var customer = new Customer(
                FAKER.name().fullName(),
                mail,
                FAKER.number().numberBetween(16, 60)
        );
        underTest.save(customer);

        var isIdFound = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(mail))
                .findFirst()
                .map(Customer::getId)
                .map(underTest::existsCustomerById)
                .orElseThrow();


        //When
        assertThat(isIdFound).isTrue();
    }


}