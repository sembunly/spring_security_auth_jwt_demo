package com.sembunly.service;

import com.sembunly.dto.ResponseErrorTemplate;
import com.sembunly.dto.UserRequest;
import com.sembunly.dto.UserResponse;

import java.util.List;

public interface UserService {
    ResponseErrorTemplate create(UserRequest userRequest);
    ResponseErrorTemplate findById(Long id);
    ResponseErrorTemplate findByUsername(String username);
    List<UserResponse> findAll();
}
