package com.sembunly.controller;

import com.sembunly.dto.ResponseErrorTemplate;
import com.sembunly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseErrorTemplate currentUser(Authentication authentication) {
        return userService.findByUsername(authentication.getName());
    }
}
