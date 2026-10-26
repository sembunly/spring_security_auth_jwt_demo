package com.sembunly.config;

import com.sembunly.constant.Constant;
import com.sembunly.model.Role;
import com.sembunly.model.User;
import com.sembunly.repository.RoleRepository;
import com.sembunly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminAccountBootstrap implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:}")
    private String username;

    @Value("${app.admin.email:}")
    private String email;

    @Value("${app.admin.password:}")
    private String password;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            return;
        }

        Role adminRole = roleRepository.findFirstByName("ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").build()));
        User admin = userRepository.findByUsernameAndStatus(username, Constant.ACT).orElseGet(() ->
                userRepository.save(User.builder()
                        .username(username)
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .status(Constant.ACT)
                        .loginAttempt(0)
                        .created(LocalDateTime.now())
                        .roles(new ArrayList<>())
                        .build()));

        List<Role> roles = admin.getRoles() == null ? new ArrayList<>() : new ArrayList<>(admin.getRoles());
        if (roles.stream().noneMatch(role -> "ADMIN".equals(role.getName()))) {
            roles.add(adminRole);
            admin.setRoles(roles);
            userRepository.save(admin);
        }
    }
}
