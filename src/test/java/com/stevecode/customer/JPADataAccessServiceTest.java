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
    }

    @Test
    void insertCustomer() {
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