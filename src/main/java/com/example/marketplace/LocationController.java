package com.example.marketplace;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class LocationController {

    private final GeoIPLocationService geoIPLocationService;

    @SneakyThrows
    @GetMapping
    public GeoIP ping(HttpServletRequest request){

        String ip = request.getRemoteAddr();

        return geoIPLocationService.getIpLocation(ip, request);
    }
}
