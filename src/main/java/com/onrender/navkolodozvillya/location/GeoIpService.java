package com.onrender.navkolodozvillya.location;

import jakarta.servlet.http.HttpServletRequest;

public interface GeoIpService {
    GeoIP getIpLocation(HttpServletRequest request);
}
