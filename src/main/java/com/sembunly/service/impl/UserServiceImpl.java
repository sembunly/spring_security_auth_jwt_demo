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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseErrorTemplate create(UserRequest userRequest) {
        this.userRequestValidation(userRequest);

        // Public registration must never allow a client to choose an elevated role.
        Role userRole = roleRepository.findFirstByName("USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("USER").build()));
        User user = User.builder()
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .email(userRequest.email())
                .fullName(userRequest.fullName())
                .roles(List.of(userRole))
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
                user.getEmail(),
                user.getFullName(),
                user.getRoles().stream().map(Role::getName).toList(),
                user.getStatus(),
                user.getLoginAttempt(),
                user.getCreated(),
                user.getUpdated()
        );

        return new ResponseErrorTemplate(Constant.SUC_MSG, Constant.SUC_CODE, userResponse);
    }

    private void userRequestValidation(UserRequest userRequest) {

        if (userRequest == null
                || ObjectUtils.isEmpty(userRequest.username())
                || ObjectUtils.isEmpty(userRequest.password())
                || ObjectUtils.isEmpty(userRequest.email())) {
            log.warn("Username, password, and email are required.");
            throw new CustomMessageException(
                    "Username, password, and email are required.", String.valueOf(HttpStatus.BAD_REQUEST.value())
            );
        }

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

    }
}
