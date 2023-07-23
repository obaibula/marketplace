package com.onrender.navkolodozvillya.auth;

import com.onrender.navkolodozvillya.config.JwtService;
import com.onrender.navkolodozvillya.token.Token;
import com.onrender.navkolodozvillya.token.TokenRepository;
import com.onrender.navkolodozvillya.user.Role;
import com.onrender.navkolodozvillya.user.User;
import com.onrender.navkolodozvillya.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.onrender.navkolodozvillya.token.TokenType.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.CUSTOMER)
                .build();

        var persistedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(persistedUser, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(); //todo: throw correct exc
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public AuthenticationResponse refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String refreshToken;
        String accessToken = null; // todo: refactor
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null; // todo: throw appropriate ex
        }
        // If a accessToken exists, then extract a refreshToken from the header
        refreshToken = authHeader.substring(7);
        // Check if we have a user with such a refreshToken in the database
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            // get user from the db
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow(); // todo: throw appropriate ex
            if (jwtService.isTokenValid(refreshToken, user)) {
                accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
            }
        }
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}













