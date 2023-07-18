package com.onrender.navkolodozvillya.location;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;



@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestDatabaseContainerConfig.class)
@TestPropertySource("classpath:application-test.properties")
class GeoIPLocationRepositoryTest {

    @Autowired
    private GeoIPLocationRepository underTest;


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
    public void test(){
        var result = underTest.getIpLocation("176.36.207.169");
        System.out.println(result.orElseThrow());
    }
}