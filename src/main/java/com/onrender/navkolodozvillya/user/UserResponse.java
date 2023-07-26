package com.onrender.navkolodozvillya.user;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role) {
}
