package com.onrender.navkolodozvillya.location;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class LocationController {

    private final GeoIPLocationService geoIPLocationService;

    @GetMapping
    public GeoIP getCity(HttpServletRequest request){
        var ipAddress = request.getRemoteAddr();
        return geoIPLocationService.getIpLocation(ipAddress, request);
    }
}
