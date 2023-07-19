package com.onrender.navkolodozvillya.integration;

import com.onrender.navkolodozvillya.config.TestDatabaseContainerConfig;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestDatabaseContainerConfig.class)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class LocationIT {

    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setUp(@Autowired DataSource data) {
        try (Liquibase liquibase = new Liquibase("db/changelog/changelog-root.xml",
                new ClassLoaderResourceAccessor(),
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(data.getConnection())))) {
            liquibase.dropAll();
            liquibase.update("prod, test");
        } catch (LiquibaseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void canGetLocationByIp() throws Exception {
        // when
        var resultActions = mockMvc.perform(
                get("/city")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.ip").value("Genereted for : 127.0.0.1"))
                .andExpect(jsonPath("$.city").value("Kyiv"));

    }

}
