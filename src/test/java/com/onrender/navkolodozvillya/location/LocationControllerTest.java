package com.onrender.navkolodozvillya.location;

import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock private GeoIpService geoIpService;
    @Mock private HttpServletRequest request;
    private LocationController underTest;

    @BeforeEach
    void setUp() {
        underTest = new LocationController(geoIpService);
    }

    @Test
    public void shouldReturnLocation(){
        // given
        String ipAddress = "80.92.227.49";
        var location = new GeoIP(ipAddress, "Kyiv", 50.458, 30.5303);
        given(geoIpService.getIpLocation(any(HttpServletRequest.class)))
                .willReturn(location);
        // when
        var result = underTest.getCity(request);
        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(location);
    }
}