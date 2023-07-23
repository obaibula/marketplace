package com.onrender.navkolodozvillya.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken) {
}
