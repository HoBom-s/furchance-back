package com.hobom.furchance.interceptor;

import com.hobom.furchance.auth.constant.AuthConstant;
import com.hobom.furchance.auth.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
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
