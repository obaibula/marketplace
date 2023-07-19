package com.onrender.navkolodozvillya.location;

import java.util.Optional;

public interface GeoIpRepository {
    Optional<GeoIP> getIpLocation(String ip);
}
