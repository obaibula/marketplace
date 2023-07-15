package com.onrender.navkolodozvillya.location;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class LocationController {

    private final GeoIpService geoIpService;

    @GetMapping
    public ResponseEntity<GeoIP> getCity(HttpServletRequest request){
        GeoIP location = geoIpService.getIpLocation(request);
        return ResponseEntity.ok(location);
    }
}
