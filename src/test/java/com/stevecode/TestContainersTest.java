package com.stevecode;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

//SpringBootTest -> Not for Unit-Tests -> initialize all Beans
//In this Test Only-Focus on DAO-Layer
@Testcontainers
public class TestContainersTest {

    //lockup-DockerHub ->latest not work with fly...
    //psql -U stevecode -d stevecode-dao-unit-test
    //l
    //c database
    //d relations

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer
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

    //assertj-core.api
    @Test
    void canStartPostgresqlDB() {
       assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }

    //https://documentation.red-gate.com/fd/api-java-184127629.html
    @Test
    void canApplyDbMigrationsWithFlyway() {
        Flyway flyway = Flyway.configure().dataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        ).load();
        flyway.migrate();
        System.out.println();

    }
}
