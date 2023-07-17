package com.onrender.navkolodozvillya.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestDatabaseContainerConfig {

    @Bean
    @ServiceConnection
    @DynamicPropertySource
    PostgreSQLContainer<?> postgresContainer(DynamicPropertyRegistry registry) {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.8");
        addAppropriateDataSources(registry, container);
        return container;
    }

    private static void addAppropriateDataSources(DynamicPropertyRegistry registry, PostgreSQLContainer<?> container) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }
}
