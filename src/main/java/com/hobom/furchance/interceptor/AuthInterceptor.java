package com.hobom.furchance.interceptor;

import com.hobom.furchance.domain.auth.constant.AuthConstant;
import com.hobom.furchance.domain.auth.util.JwtUtils;
import com.hobom.furchance.exception.CustomException;
import com.hobom.furchance.exception.constant.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String method = request.getMethod();

        if(HttpMethod.GET.matches(method)){
            return true;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorMessage.TOKEN_INVALID);
        }

        String accessToken = authHeader.substring(7);

        if (!jwtUtils.isTokenValid(accessToken)) {
            accessToken = jwtUtils.regenerateAccessToken(accessToken);
        }

        Long extractedUserId = jwtUtils.extractUserId(accessToken);
        request.setAttribute(AuthConstant.VERIFIED_USER_ID, extractedUserId);

        return true;
    }
}
