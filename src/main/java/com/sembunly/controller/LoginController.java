package com.sembunly.controller;

import com.sembunly.auth.LoginRequest;
import com.sembunly.auth.TokenResponse;
import com.sembunly.exception.CustomMessageException;
import com.sembunly.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/account")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        final Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        } catch (AuthenticationException exception) {
            throw new CustomMessageException("Invalid username or password.",
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }

        return new TokenResponse(jwtService.createToken(authentication), "Bearer",
                jwtService.getExpirationSeconds());
    }
}
