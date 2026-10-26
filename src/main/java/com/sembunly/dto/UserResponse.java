package com.sembunly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
        String username,
        String email,
        @JsonProperty("full_name")String fullName,
        @JsonProperty("roles")List<String> roles,
        String status,
        @JsonProperty("login_attempt") int loginAttempt,
        LocalDateTime created,
        LocalDateTime updated
        ){

}
