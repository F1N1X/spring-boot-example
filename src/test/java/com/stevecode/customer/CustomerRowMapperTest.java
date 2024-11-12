package com.stevecode.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        //Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet mock = mock(ResultSet.class);
        when(mock.getInt("id")).thenReturn(1);
        when(mock.getInt("age")).thenReturn(18);
        when(mock.getString("name")).thenReturn("TestName");
        when(mock.getString("email")).thenReturn("email@test.com");

        //When
        Customer actual = customerRowMapper.mapRow(mock, 1);

        //Then
        Customer expected = new Customer(
                1,
                "TestName",
                "email@test.com",
                18
        );

        //Assertions.assertThat
        assertThat(expected).isEqualTo(actual);


    }
}