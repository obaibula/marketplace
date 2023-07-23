package com.onrender.navkolodozvillya.auth;

// todo: impl validation, only unique email are allowed
public record RegisterRequest(String firstName,
                              String lastName,
                              String email,
                              String password) {
}
