package com.stevecode.customer;

import com.stevecode.exception.DuplicateResourceException;
import com.stevecode.exception.RequestValidationException;
import com.stevecode.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        //When
        underTest.getAllCustomers();
        //Then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void canGetCustomer() {
        //Given
        int id = 10;
        Customer customer = new Customer(
                id,
                "test",
                "test@test",
                11
        );
        //mokito.when....
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        //When
        Customer actual = underTest.getCustomer(id);
        //Then

        //org.assertj.core.api
        //Assertions
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowWhenCustomerReturnEmptyOptional() {
        //Given
        int id = 10;

        //mokito.when....
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());
        //When
        //Then
        assertThatThrownBy(() ->underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage( "Customer with id [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        //Given
        String email = "test@test.com";

        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "testName",
                email,
                16
        );

        //Then
        underTest.addCustomer(request);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );

        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        //When
        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }


    @Test
    void willTrowWhenEmailExistsWhileAddingCustomer() {
        //Given
        String email = "test@test.com";
        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "testName",
                email,
                16
        );

        //When
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage( "Email already taken");

        //Then
        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        //Given
        var id = 10;
        when(customerDao.existPersonWithId(id)).thenReturn(true);
        //When
        underTest.deleteCustomerById(id);
        //Then
        verify(customerDao).deleteCustomerById(id);
    }


    @Test
    void ThrowExceptionNotFoundCustomerByDeleteCustomerById() {
        //Given
        var id = 10;
        when(customerDao.existPersonWithId(id)).thenReturn(false);
        //When
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage( "Customer with ID [%s] not exist".formatted(id));
        //Then
        verify(customerDao, never()).deleteCustomerById(id);
    }

    @Test
    void ResourceNotFoundExceptionByDeleteCustomerById() {
        //Given
        Integer id = 10;
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "test",
                "testMail",
                20
        );

    }

    @Test
    void throwExceptionOnGetCustomer() {

        var id = 10;

        when(customerDao.selectCustomerById(id)).thenThrow(ResourceNotFoundException.class);

        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void IdNotExistExceptionToUpdateCustomer() {
        var id = 10;
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "Rudi",
                "test@mail.com",
                12
        );

        when(customerDao.existPersonWithId(id)).thenReturn(false);

        assertThatThrownBy(()->{underTest.updateCustomer(id,request);})
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with ID [%s] not exist".formatted(id));


        verify(customerDao,never()).updateCustomer(any());
    }

    @Test
    void CanUpdateAllCustomerProperties() {
        //Given
        int id = 10;
        Customer customer = new Customer(
                id,
                "test",
                "test@test",
                11
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", "alexnadro@gmail.com", 14);

        when(customerDao.existsPersonWithEmail(updateRequest.email())).thenReturn(false);
        when(customerDao.existPersonWithId(id)).thenReturn(true);

        //When
        underTest.updateCustomer(id, updateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer captuaredCustomer = customerArgumentCaptor.getValue();

        assertThat(captuaredCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(captuaredCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(captuaredCustomer.getName()).isEqualTo(customer.getName());
    }

    @Test
    void CanUpdateOnlyCustomerName() {
        //Given
        int id = 10;
        Customer customerData = new Customer(
                id,
                "testData",
                "test@test",
                11
        );


        when(customerDao.existPersonWithId(any())).thenReturn(true);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customerData));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", null, null);



        //When
        underTest.updateCustomer(id, updateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer captuaredCustomer = customerArgumentCaptor.getValue();

        assertThat(captuaredCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(captuaredCustomer.getEmail()).isEqualTo(customerData.getEmail());
        assertThat(captuaredCustomer.getAge()).isEqualTo(customerData.getAge());
    }

    @Test
    void CanUpdateOnlyCustomerEmail() {
        //Given
        int id = 10;
        Customer customerData = new Customer(
                id,
                "testData",
                "test@test",
                11
        );


        when(customerDao.existPersonWithId(any())).thenReturn(true);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customerData));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, "NewMail", null);



        //When
        underTest.updateCustomer(id, updateRequest);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer captuaredCustomer = customerArgumentCaptor.getValue();

        assertThat(captuaredCustomer.getName()).isEqualTo(customerData.getName());
        assertThat(captuaredCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(captuaredCustomer.getAge()).isEqualTo(customerData.getAge());
    }

    @Test
    void willThrowWhenTryToUpdateCustomerEmailWhenAlreadyTaken() {
        //Given
        int id = 10;
        Customer customerData = new Customer(
                id,
                "testData",
                "test@test",
                11
        );


        when(customerDao.existPersonWithId(any())).thenReturn(true);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customerData));
        when(customerDao.existsPersonWithEmail(any())).thenReturn(true);

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, "NewMail", null);


        //When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email [%s] is already taken".formatted(updateRequest.email()));

        //Then

        verify(customerDao, never()).updateCustomer(any());
    }


    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        //Given
        int id = 10;
        Customer customer = new Customer(
                id,
                "test",
                "test@test",
                11
        );
        when(customerDao.existPersonWithId(any())).thenReturn(true);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(customer.getName(), customer.getEmail(), customer.getAge());


        //When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("Customer with ID [%s] nothing to update".formatted(customer.getId()));

        //Then
      verify(customerDao, never()).updateCustomer(any());
    }

}