package com.sembunly.service.impl;

import com.sembunly.constant.Constant;
import com.sembunly.dto.ResponseErrorTemplate;
import com.sembunly.dto.UserRequest;
import com.sembunly.dto.UserResponse;
import com.sembunly.exception.CustomMessageException;
import com.sembunly.model.Role;
import com.sembunly.model.User;
import com.sembunly.repository.RoleRepository;
import com.sembunly.repository.UserRepository;
import com.sembunly.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseErrorTemplate create(UserRequest userRequest) {
        // TODO: 25/10/2026 validation data of user request
        this.userRequestValidation(userRequest);

        List<Role> roles = roleRepository.findAllByNameIn(userRequest.roles());
        User user = User.builder()
                .username(userRequest.username())
                .password(userRequest.password())
                .email(userRequest.email())
                .fullName(userRequest.fullName())
                .roles(roles)
                .loginAttempt(0)
                .status(Constant.ACT)
                .created(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return this.userMapper(user);
    }


    @Override
    public ResponseErrorTemplate findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        var msg = String.format("User id %s not found.", id);
        return userOptional.map(this::userMapper)
                .orElse(new ResponseErrorTemplate(msg, String.valueOf(HttpStatus.NOT_FOUND.value()), new Object()));
    }

    @Override
    public ResponseErrorTemplate findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsernameAndStatus(username, Constant.ACT);
        var msg = String.format("Username %s not found.", username);
        return userOptional.map(this::userMapper)
                .orElse(new ResponseErrorTemplate(msg, String.valueOf(HttpStatus.NOT_FOUND.value()), new Object()));
    }

    private ResponseErrorTemplate userMapper(User user) {
        UserResponse userResponse = new UserResponse(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullName(),
                user.getRoles().stream().map(Role::getName).toList(),
                user.getStatus(),
                user.getLoginAttempt(),
                user.getCreated(),
                user.getCreated()
        );

        return new ResponseErrorTemplate(Constant.SUC_MSG, Constant.SUC_CODE, userResponse);
    }

    private void userRequestValidation(UserRequest userRequest) {

        if (ObjectUtils.isEmpty(userRequest.password())) {
            log.warn("Password must not be null or blank.");
            throw new CustomMessageException(
                    "Password must not be null or blank.", String.valueOf(HttpStatus.BAD_REQUEST.value())
            );
        }

        // TODO 25/10/2026 validate username or email duplicate
        Optional<User> user = userRepository.findByUsernameOrEmail(
                userRequest.username(),
                userRequest.email()
        );

        if (user.isPresent()) {
            log.warn("Username or Email can't be duplicate.");
            throw new CustomMessageException(
                    "Username or Email can't be duplicate.", String.valueOf(HttpStatus.BAD_REQUEST.value())
            );
        }

        // TODO 25/10/2026 validate username or email duplicate
        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
        for (var role : userRequest.roles()) {
            if(!roles.contains(role)){
                log.warn("Role is invalid request.");
                throw new CustomMessageException(
                        "Row is invalid.", String.valueOf(HttpStatus.BAD_REQUEST.value())
                );
            }
        }
    }
}
