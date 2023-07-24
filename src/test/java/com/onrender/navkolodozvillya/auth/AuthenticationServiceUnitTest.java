package com.onrender.navkolodozvillya.auth;

import com.onrender.navkolodozvillya.cart.CartService;
import com.onrender.navkolodozvillya.config.JwtService;
import com.onrender.navkolodozvillya.exception.InvalidTokenException;
import com.onrender.navkolodozvillya.exception.MissingAuthorizationHeaderException;
import com.onrender.navkolodozvillya.exception.UserAlreadyExistsException;
import com.onrender.navkolodozvillya.exception.UserNotFoundException;
import com.onrender.navkolodozvillya.token.Token;
import com.onrender.navkolodozvillya.token.TokenRepository;
import com.onrender.navkolodozvillya.user.User;
import com.onrender.navkolodozvillya.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.onrender.navkolodozvillya.user.Role.CUSTOMER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceUnitTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private CartService cartService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private HttpServletRequest httpServletRequest;
    private AuthenticationService underTest;


    @BeforeEach
    void setUp() {
        underTest = new AuthenticationService(
                userRepository,
                tokenRepository,
                passwordEncoder,
                jwtService,
                cartService,
                authenticationManager);
    }

    @Test
    public void shouldRegisterUser(){
        // given
        var requst = new RegisterRequest(
                "John",
                "Doe",
                "user@mail.com",
                "PassWord1#");
        var mockUser = createMockUser();
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString()))
                .willReturn("encoded_password");
        given(userRepository.save(any(User.class)))
                .willReturn(mockUser);
        
        // when
        var response = underTest.register(requst);

        // then
        verify(jwtService, times(1)).generateToken(mockUser);
        verify(jwtService, times(1)).generateRefreshToken(mockUser);
        verify(cartService, times(1)).createCart(mockUser);
        assertThat(response).isNotNull();
    }

    @Test
    public void shouldThrowExceptionWhenUserIsAlreadyRegistered(){
        // given
        var request = new RegisterRequest(
                "John",
                "Doe",
                "user@mail.com",
                "PassWord1#");
        var mockUser = createMockUser();
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(mockUser));

        // then
        assertThatThrownBy(() -> underTest.register(request))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User already exists with email: user@mail.com");
    }

    @Test
    public void shouldAuthenticateUser(){
        // given
        var request = new AuthenticationRequest(
                "user@mail.com",
                "PassWord1#");
        var mockUser = createMockUser();
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(mockUser));
        // when
        var response = underTest.authenticate(request);
        // then
        verify(jwtService, times(1)).generateToken(mockUser);
        verify(jwtService, times(1)).generateRefreshToken(mockUser);
        assertThat(response).isNotNull();
    }

    @Test
    public void shouldRevokeAllTokensIfAnyExistWhenAuthenticatingUser(){
        // given
        var request = new AuthenticationRequest(
                "user@mail.com",
                "PassWord1#");
        var mockUser = createMockUser();
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(mockUser));
        var token = new Token();
        given(tokenRepository.findAllValidTokensByUser(anyLong()))
                .willReturn(List.of(token));
        // when
        var response = underTest.authenticate(request);
        // then
        verify(jwtService, times(1)).generateToken(mockUser);
        verify(jwtService, times(1)).generateRefreshToken(mockUser);
        assertThat(response).isNotNull();
        assertThat(token.isExpired()).isTrue();
        assertThat(token.isRevoked()).isTrue();

    }

    @Test
    public void shouldRefreshToken(){
        // given
        var mockUser = createMockUser();
        given(httpServletRequest.getHeader(anyString()))
                .willReturn("Bearer refresh_valid_token");
        given(jwtService.extractUsername(anyString()))
                .willReturn("user@mail.com");
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(mockUser));
        given(jwtService.isTokenValid(anyString(), any(User.class)))
                .willReturn(true);
        // when
        var response = underTest.refreshToken(httpServletRequest);
        // then
        verify(jwtService, times(1)).generateToken(mockUser);
        assertThat(response).isNotNull();
    }

    @Test
    public void shouldThrowExceptionWhenCanNotFindAnyValidToken(){
        // given
        given(httpServletRequest.getHeader(anyString()))
                .willReturn("refresh_invalid_token"); // should start with Bearer
        // then
        assertThatThrownBy(() -> underTest.refreshToken(httpServletRequest))
                .isInstanceOf(MissingAuthorizationHeaderException.class)
                .hasMessage("Missing or invalid 'Authorization' header");
    }

    @Test
    public void shouldThrowExceptionWhenCanNotExtractUserByToken(){
        // given
        given(httpServletRequest.getHeader(anyString()))
                .willReturn("Bearer refresh_valid_token");
        given(jwtService.extractUsername(anyString()))
                .willReturn(null);
        // then
        assertThatThrownBy(() -> underTest.refreshToken(httpServletRequest))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessage("Invalid refresh token");
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExist(){
        // given
        given(httpServletRequest.getHeader(anyString()))
                .willReturn("Bearer refresh_valid_token");
        given(jwtService.extractUsername(anyString()))
                .willReturn("user@mail.com");
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> underTest.refreshToken(httpServletRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with email: user@mail.com");

    }

    @Test
    public void shouldThrowExceptionWhenTokenIsNotValidForCurrentUser(){
        // given
        var mockUser = createMockUser();
        given(httpServletRequest.getHeader(anyString()))
                .willReturn("Bearer refresh_valid_token");
        given(jwtService.extractUsername(anyString()))
                .willReturn("user@mail.com");
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(mockUser));
        given(jwtService.isTokenValid(anyString(), any(User.class)))
                .willReturn(false);
        // then
        assertThatThrownBy(() -> underTest.refreshToken(httpServletRequest))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessage("Invalid refresh token");

    }

    private User createMockUser() {
        return User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("user@mail.com")
                .password("encoded_password")
                .role(CUSTOMER)
                .build();
    }
}