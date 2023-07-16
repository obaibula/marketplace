package com.onrender.navkolodozvillya.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class GeoIPLocationRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<GeoIP> getIpLocation(String ip) {
        var sql = """
                select latitude, longitude, city_name
                from geoip2_network net
                                
                left join geoip2_location location on (
                  net.geoname_id = location.geoname_id
                  and location.locale_code = 'en'
                )
                where network >> ?;
                """;

        // The network is of CIDR type, so we need to explicitly specify the SQL type as "OTHER".
        var position = jdbcTemplate.query(
                sql,
                new Object[]{ip},
                new int[]{Types.OTHER},
                getGeoIPRowMapper(ip));


        var geoIPOptional = position.stream().findAny();
        createLogs(ip, geoIPOptional.isPresent());
        return geoIPOptional;
    }

    private static void createLogs(String ip, boolean isPresent) {
        if (isPresent) {
            log.info("Location retrieved for IP {}", ip);
        } else {
            log.info("No location found for IP: {}", ip);
            log.info("Created default location for Kyiv");
        }
    }

    private static RowMapper<GeoIP> getGeoIPRowMapper(String ip) {
        return (rs, rowNum) -> new GeoIP(ip,
                rs.getString("city_name"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"));
    }
}