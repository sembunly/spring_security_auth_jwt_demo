package com.sembunly.service.impl;

import com.sembunly.dto.UserRequest;
import com.sembunly.dto.UserResponse;
import com.sembunly.model.Role;
import com.sembunly.model.User;
import com.sembunly.repository.RoleRepository;
import com.sembunly.repository.UserRepository;
import com.sembunly.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse create(UserRequest userRequest) {
        // TODO: 25/10/2026 validation date or user request
        this.userRequestValidation(userRequest);
        return null;
    }


    @Override
    public UserResponse findById(Long id) {
        return null;
    }

    @Override
    public UserResponse findByUsername(String username) {
        return null;
    }

    private void userRequestValidation(UserRequest userRequest) {

        if (ObjectUtils.isEmpty(userRequest.password())) {
            log.warn("Password must not be null or blank");
        }

        // TODO 25/10/2026 validate username or email duplicate
        Optional<User> user = userRepository.findByUsernameOrEmail(
                userRequest.username(),
                userRequest.email()
        );

        if (user.isPresent()) {
            log.warn("Username or Email can't be duplicate");
        }

        // TODO 25/10/2026 validate username or email duplicate
        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
        /*
        List<String> roleList = new ArrayList<>();

        for (Role role : roles) {
            roleList.add(role.getName());
        }
         */
        for (var role : userRequest.roles()) {
            if(!roles.contains(role)){
                log.warn("Role is invalid request.");
            }
        }
    }
}
