package com.example.marketplace;

import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class GeoLocationConfig {
    private static DatabaseReader reader = null;
    private final ResourceLoader resourceLoader;

    @Bean
    public DatabaseReader databaseReader(){
        try {
            var resource = resourceLoader.getResource("classpath:maxmind/GeoLite2-City.mmdb");
            var inputStream = resource.getInputStream();

            return reader = new DatabaseReader.Builder(inputStream)
                    .fileMode(Reader.FileMode.MEMORY)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
