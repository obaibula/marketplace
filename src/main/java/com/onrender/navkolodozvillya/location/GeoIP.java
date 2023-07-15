package com.onrender.navkolodozvillya.location;

public record GeoIP(
        String ip,
        String city,
        Double latitude,
        Double longitude
) {
}
