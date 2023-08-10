package com.onrender.navkolodozvillya.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Controller",
        description = "Authentication related APIs")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin //todo: expose to appropriate domains
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register new user.",
            description = """
                    An API call to register a new user.
                    The expected result is an access and refresh token.
                    """)
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request) {
        return ResponseEntity
                .ok(authenticationService.register(request));
    }

    @Operation(summary = "Authenticate a user.",
            description = """
                    An API call to authenticate a new user.
                    The expected result is an access and refresh token.
                    """)
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity
                .ok(authenticationService.authenticate(request));
    }

    @Operation(summary = "Refresh the expired access token.",
            description = """
                    An API call to refresh expired access token.
                    The expected result is an new access and same refresh token.
                    """)
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request
    ) {
        return ResponseEntity
                .ok(authenticationService.refreshToken(request));
    }
}
