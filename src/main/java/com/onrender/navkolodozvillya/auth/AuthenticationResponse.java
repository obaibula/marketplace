package com.onrender.navkolodozvillya.auth;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken) {
}
