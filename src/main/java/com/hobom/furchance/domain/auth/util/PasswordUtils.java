package com.hobom.furchance.domain.auth.util;

import com.hobom.furchance.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String rawPassword) {

        return passwordEncoder.encode(rawPassword);
    }

    public boolean validatePassword(User foundUser, String rawPassword) {

        return passwordEncoder.matches(rawPassword, foundUser.getPassword());
    }
}
