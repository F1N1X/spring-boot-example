package com.stevecode.customer;

import com.stevecode.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

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
                .map(Customer::getId)
                .map(underTest::existsCustomerById)
                .orElseThrow();


        //When
        assertThat(isIdFound).isTrue();
    }

    @Test
    void existsCustomerById() {
    }


}