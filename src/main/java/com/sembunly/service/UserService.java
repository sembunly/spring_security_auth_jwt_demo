package com.sembunly.service;

import com.sembunly.dto.UserRequest;
import com.sembunly.dto.UserResponse;

public interface UserService {
    UserResponse create(UserRequest userRequest);
    UserResponse findById(Long id);
    UserResponse findByUsername(String username);
}
