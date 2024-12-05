package com.stevecode;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class TestContainersTest extends AbstractTestcontainers {

    @Test
    void canStartPostgresqlDB() {
       assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }

    protected static final Faker faker = new Faker();
}
