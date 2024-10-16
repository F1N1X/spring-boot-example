package com.stevecode;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
@Testcontainers
public abstract class AbstractTestcontainers {
    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        ).load();
        flyway.migrate();
        System.out.println();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("stevecode-dao-unit-test")
            .withUsername("stevecode")
            .withPassword("password");


    //Its the Source-Path -> application.yml
    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.datasource.url",
                postgreSQLContainer::getJdbcUrl);
        registry.add(
                "spring.datasource.username",
                postgreSQLContainer::getUsername);
        registry.add(
                "spring.datasource.url",
                postgreSQLContainer::getPassword);
    }

}
