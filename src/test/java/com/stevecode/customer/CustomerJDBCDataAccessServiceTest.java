package com.stevecode.customer;

import com.stevecode.TestContainersTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


//right -> Generate Test setBefore
class CustomerJDBCDataAccessServiceTest extends TestContainersTest {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        //For Each Test = new Instance
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        //Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().emailAddress()+ UUID.randomUUID(),
                faker.number().numberBetween(16,60)
        );

        //Method to Test
        underTest.insertCustomer(customer);

        //When
        List<Customer> customers = underTest.selectAllCustomers();

        //Then
        //assertj
        assertThat(customers.isEmpty()).isFalse();
    }

    @Test
    void selectCustomerById() {
        //Given
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                faker.number().numberBetween(16,60)
        );
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Customer> actual =
                underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(
                c -> {
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getId()).isEqualTo(customer.getId());
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                }
        );
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        //Given
        int id = -1;

        //When
        var actual = underTest.selectCustomerById(id);

        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        //Test for Insert
    }

    @Test
    void existsPersonWithEmail() {
    }

    @Test
    void existPersonWithId() {
    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void updateCustomer() {
    }
}