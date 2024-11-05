package com.stevecode.customer;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class JPADataAccessServiceTest {

    private JPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerMockRepository;

    @BeforeEach
    void setUp() {
        //close after Test
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new JPADataAccessService(customerMockRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        //after each test -> fresh mock
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        //Test with Mockito that all calls right methods

        //When
        underTest.selectAllCustomers();

        //Then
        verify(customerMockRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {
        //Given
        int id = 1;
        //When
        underTest.selectCustomerById(id);
        //Then
        verify(customerMockRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        //Given
        Customer customer = new Customer(
                1,
                "test",
                "test@mail.com",
                1
        );
        //When
        underTest.insertCustomer(customer);

        //Then
        verify(customerMockRepository).save(customer);
    }

    @Test
    void existsPersonWithEmail() {
        //Given
        String email = "test@test.de";

        //When
        underTest.existsPersonWithEmail(email);

        //Then
        verify(customerMockRepository).existsCustomerByEmail(email);
    }

    @Test
    void existPersonWithId() {
        //Given
        int id = 1;
        //When
        underTest.existPersonWithId(id);
        //Then
        verify(customerMockRepository).existsCustomerById(id);
    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void updateCustomer() {
    }
}