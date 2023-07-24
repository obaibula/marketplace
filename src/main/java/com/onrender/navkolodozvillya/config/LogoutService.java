package com.onrender.navkolodozvillya.config;

import com.onrender.navkolodozvillya.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        jwtToken = authHeader.substring(7);
        var pesrsistedToken = tokenRepository.findByToken(jwtToken)
                .orElse(null);
        if(pesrsistedToken != null){
            pesrsistedToken.setExpired(true);
            pesrsistedToken.setRevoked(true);
            tokenRepository.save(pesrsistedToken);
        }
    }
}
