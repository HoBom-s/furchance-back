package com.hobom.furchance.auth.util;

import com.hobom.furchance.user.entity.User;
import com.hobom.furchance.user.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder;

    private final UserValidationService userValidationService;

    public String encodePassword(String rawPassword) {

        return passwordEncoder.encode(rawPassword);
    }

    public boolean validatePassword(User foundUser, String rawPassword) {

        return passwordEncoder.matches(rawPassword, foundUser.getPassword());
    }
}
