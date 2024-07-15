package com.hobom.furchance.util;

import com.hobom.furchance.auth.constant.AuthConstant;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class CommonUtils {

    public static Long getVerifiedUserId(HttpServletRequest request) {

        Object attribute = request.getAttribute(AuthConstant.VERIFIED_USER_ID);

        return (Long) attribute;
    }

    public static void validateUser(Long verifiedUserId, Long givenUserId) {

        if(!Objects.equals(verifiedUserId, givenUserId)) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorMessage.PERMISSION);
        }
    }
}
