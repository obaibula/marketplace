package com.onrender.navkolodozvillya.auth;

// todo: impl validation
public record RegisterRequest(String firstName,
                              String lastName,
                              String email,
                              String password) {
}
