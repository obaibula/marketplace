package com.onrender.navkolodozvillya.location;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeoIpServiceImplTest {
    @Mock
    private GeoIpRepository repository;
    @Mock
    private HttpServletRequest request;

    private GeoIpService underTest;

    @BeforeEach
    void setUp() {
        underTest = new GeoIpServiceImpl(repository);
    }

    @Test
    void canGetIpLocationWhenIpExists() {
        // given
        String ipAddress = "80.92.227.49";
        var location = new GeoIP(ipAddress, "Kyiv", 50.458, 30.5303);
        given(request.getRemoteAddr())
                .willReturn(ipAddress);
        given(repository.getIpLocation(anyString()))
                .willReturn(Optional.of(location));
        // when
        var result = underTest.getIpLocation(request);
        // then
        verify(repository, times(1)).getIpLocation(ipAddress);
        verify(request, times(1)).getRemoteAddr();
        assertThat(result).isEqualTo(location);
    }

    @Test
    void canGetIpLocationWhenIpDoesNotExistWithDefaultRecord() {
        // given
        String ipAddress = "80.92.227.49";
        given(request.getRemoteAddr())
                .willReturn(ipAddress);
        given(repository.getIpLocation(anyString()))
                .willReturn(Optional.empty());

        var expectedLocation = new GeoIP("Genereted for : 80.92.227.49",
                "Kyiv", 50.4501, 30.5234);
        // when
        var result = underTest.getIpLocation(request);
        // then
        verify(repository, times(1)).getIpLocation(ipAddress);
        verify(request, times(1)).getRemoteAddr();
        assertThat(result).isEqualTo(expectedLocation);
    }

    @Test
    void canGetIpLocationWhenIpIsNullWithDefaultRecord() {
        // given
        given(repository.getIpLocation(anyString()))
                .willReturn(Optional.empty());
        var expectedLocation = new GeoIP("Genereted for : 0:0:0:0:0:0:0:1",
                "Kyiv", 50.4501, 30.5234);
        // when
        var result = underTest.getIpLocation(request);
        // then
        verify(repository, times(1)).getIpLocation("0:0:0:0:0:0:0:1");
        verify(request, times(1)).getRemoteAddr();
        assertThat(result).isEqualTo(expectedLocation);
    }
}