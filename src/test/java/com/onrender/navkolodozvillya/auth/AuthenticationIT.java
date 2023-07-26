package com.onrender.navkolodozvillya.auth;

import com.onrender.navkolodozvillya.config.JwtService;
import com.onrender.navkolodozvillya.config.TestDatabaseContainerConfig;
import com.onrender.navkolodozvillya.token.Token;
import com.onrender.navkolodozvillya.token.TokenRepository;
import com.onrender.navkolodozvillya.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.onrender.navkolodozvillya.token.TokenType.BEARER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestDatabaseContainerConfig.class)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
class AuthenticationIT {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtService jwtService;

    @Autowired private TokenRepository tokenRepository;
    @Autowired private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    /*@Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/city"))
                .andExpect(status()
                        .isForbidden());
    }*/

    @Test
    public void shouldAllowAccessToAuthenticatedUsers() throws Exception {

        String token = generateTestToken();
        System.err.println(token);
        mvc.perform(MockMvcRequestBuilders.get("/city")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private String generateTestToken() {
        // by registered user "user@mail.com"

        UserDetails userDetails = userDetailsService
                .loadUserByUsername("user@mail.com");
        String token = jwtService
                .generateToken(userDetails);
        saveUserToken(userDetails, token);

        return token;
    }

    private void saveUserToken(UserDetails userDetails, String jwtToken) {
        var user =  userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();
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