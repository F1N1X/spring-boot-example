package com.stevecode;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class TestContainersTest {

    //lockup-DockerHub ->latest not work with fly...

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("stevecode-dao-unit-test")
            .withUsername("stevecode")
            .withPassword("password");

    //assertj-core.api
    @Test
    void canStartPostgresqlDB() {
       assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }
}
