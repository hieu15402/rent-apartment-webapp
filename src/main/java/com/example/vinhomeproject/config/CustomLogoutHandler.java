package com.example.vinhomeproject.config;

import com.example.vinhomeproject.models.Token;
import com.example.vinhomeproject.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    @Autowired
    private TokenRepository tokenRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private  UserDetailsService userDetailsService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String access_token;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        access_token = authHeader.substring(7);
        Optional<Token> optionalToken = tokenRepo.findByToken(access_token);
        if (optionalToken.isPresent()) {
            Token storedToken = optionalToken.get();
            userEmail = jwtService.extractUsername(access_token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(access_token, userDetails)
                    && !storedToken.isExpired() && !storedToken.isRevoked()) {
                storedToken.setExpired(true);
                storedToken.setRevoked(true);
                tokenRepo.save(storedToken);
                SecurityContextHolder.clearContext();
            }
        }

    }
}
