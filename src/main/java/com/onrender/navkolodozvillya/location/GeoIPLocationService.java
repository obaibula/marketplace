package com.onrender.navkolodozvillya.location;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class GeoIPLocationService {
    private final JdbcTemplate jdbcTemplate;
    @SneakyThrows
    public GeoIP getIpLocation(String ip, HttpServletRequest request) {

        // In this case we are safe from sql injection, because we retrieve ip-address from the request (?)
        // todo: Find out which class is suitable for PreparedStatement in the case of PostgreSQL cidr type.
        var sql = """
                select latitude, longitude, city_name
                from geoip2_network net
                left join geoip2_location location on (
                  net.geoname_id = location.geoname_id
                  and location.locale_code = 'en'
                )
                where network >>
                """;

        sql = sql + "'" + ip + "';";

        var position = jdbcTemplate.query(sql, new GeoIPRowMapper(ip));

        return position.stream()
                .findFirst()
                .orElse(new GeoIP("Genereated", "Kyiv", 50.4501, 30.5234));
    }
}

@RequiredArgsConstructor
class GeoIPRowMapper implements RowMapper<GeoIP>{
    private final String ip;

    @Override
    public GeoIP mapRow(ResultSet rs, int rowNum) throws SQLException {
        var latitude = rs.getDouble("latitude");
        var longitude = rs.getDouble("longitude");
        var city = rs.getString("city_name");

        return new GeoIP(ip, city, latitude, longitude);
    }
}