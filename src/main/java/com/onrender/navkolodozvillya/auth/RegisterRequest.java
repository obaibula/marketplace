package com.onrender.navkolodozvillya.auth;

import jakarta.validation.constraints.*;

public record RegisterRequest(

        @Pattern(regexp = "^[\\u0400-\\u04FF]+$",
                message = "Invalid first name: must contain only Ukrainian characters")
        String firstName,
        @Pattern(regexp = "^[\\u0400-\\u04FF]+$",
                message = "Invalid last name: must contain only Ukrainian characters")
        String lastName,
        @Email(message = "Invalid email")
        @NotNull(message = "Email must not be null")
        String email,
        @NotNull(message = "Password must not be null")
        @Pattern(regexp = "^(?=.*\\d)(?=.*\\p{Lower})(?=.*\\p{Upper})(?=.*\\p{Punct}).*$",
                message = "Invalid password: must contain numbers, " +
                        "lowercase and uppercase latin letters, " +
                        "and special symbols")
        @Size(min = 8, max = 64, message = "Invalid password: must be of 8-64 characters")
        String password) {
}
