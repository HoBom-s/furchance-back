package com.hobom.furchance.util;

import com.hobom.furchance.auth.constant.AuthConstant;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

public class CommonUtils {

    public static Long getVerifiedUserId(HttpServletRequest request) {

        Object attribute = request.getAttribute(AuthConstant.VERIFIED_USER_ID);
        return (Long) attribute;
    }

    public static void validateUser(Long verifiedUserId, Long givenUserId) {

        if(!Objects.equals(verifiedUserId, givenUserId)) {
            throw new RuntimeException("Failed: only the account owner is permitted to update");
        }
    }
}
