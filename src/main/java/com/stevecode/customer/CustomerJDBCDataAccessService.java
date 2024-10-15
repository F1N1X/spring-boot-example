package com.stevecode.customer;

import com.stevecode.exception.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao{

    private JdbcTemplate jdbcTemplate;
    private CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        if (!existPersonWithId(id))
            throw new ResourceNotFoundException("Customer with id " + id + " not found");


        var sql = """
                SELECT id, name, email, age
                FROM customer
                where id = ?
                """;

        //for Object use query behind
        return jdbcTemplate.query(sql,customerRowMapper,id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?,?,?)
                """;
        int result = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        System.out.println("jdbcTemplate.update " + result );
    }

    //Refactorable
    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = "SELECT COUNT(*) FROM customer WHERE email = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{email},Integer.class) != 0;
    }

    //Refactorable
    @Override
    public boolean existPersonWithId(Integer id) {
        var sql = """
                SELECT COUNT(*) FROM customer WHERE id = ? 
                """;
        return jdbcTemplate.queryForObject(sql,new Object[]{id}, Integer.class) > 0;
    }

    @Override
    public void deleteCustomerById(Integer id) {

        if (!existPersonWithId(id))
            throw new ResourceNotFoundException("Customer with id " + id + " not found");

        var sql = """
                DELETE FROM customer WHERE id = ?
                """;

        jdbcTemplate.update(sql,id);
    }

    @Override
    public void updateCustomer(Customer updateCustomer) {
        if (updateCustomer.getEmail() != null) {
            var sql ="Update customer set email = ? where id = ?";
            jdbcTemplate.update(sql,updateCustomer.getEmail(),updateCustomer.getId());
        }
        if (updateCustomer.getAge() != null) {
            var sql ="Update customer set age = ? where id = ?";
            jdbcTemplate.update(sql,updateCustomer.getAge(),updateCustomer.getId());
        }
        if (updateCustomer.getName() != null) {
            var sql = "Update customer set name = ? where id = ?";
            jdbcTemplate.update(sql,updateCustomer.getName(),updateCustomer.getId());
        }
    }
}
