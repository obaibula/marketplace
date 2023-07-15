package com.example.marketplace.location;

public record GeoIP(
        String city,
        Double latitude,
        Double longitude
) {
}
