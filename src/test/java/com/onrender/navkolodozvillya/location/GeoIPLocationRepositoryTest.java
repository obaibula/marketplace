package com.onrender.navkolodozvillya.location;

import com.onrender.navkolodozvillya.config.TestDatabaseContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestDatabaseContainerConfig.class)
@TestPropertySource("classpath:application-test.properties")
class GeoIPLocationRepositoryTest {

    @Autowired
    private GeoIPLocationRepository underTest;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void test(){
        var sql = """
                select geoname_id from geoip2_network where geoname_id = 12345;
                """;
        var count = jdbcTemplate.queryForObject(sql, int.class);

        System.err.println(count + " ***************************************");
    }
}