package com.hobom.furchance.user.util;

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

}
