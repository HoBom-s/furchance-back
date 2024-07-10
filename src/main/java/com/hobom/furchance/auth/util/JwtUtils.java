package com.hobom.furchance.auth.util;

import com.hobom.furchance.auth.service.RedisService;
import com.hobom.furchance.user.entity.User;
import com.hobom.furchance.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Transactional
@RequiredArgsConstructor
public class JwtUtils {

    private final RedisService redisService;

    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token-exp}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.refresh-token-exp}")
    private Long refreshTokenExpirationTime;

    @Value("${spring.application.name}")
    private String serviceName;

    private Key getSigningKey() {

        byte[] keyBytes = jwtSecret.getBytes();

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String createToken(String subject, long expirationTime, Map<String, Long> userIdClaim) {

        return Jwts.builder()
                .setSubject(subject)
                .setClaims(userIdClaim)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    private Map<String, Long> createUserIdClaim(User user) {

        Map<String, Long> userIdClaim = new HashMap<>();

        userIdClaim.put("userId", user.getId());

        return userIdClaim;
    }

    public String generateToken(User user, String tokenFlag) {

        Map<String, Long> userIdClaim = createUserIdClaim(user);

        if (tokenFlag.equals("access")) {
            return createToken(user.getNickname(), accessTokenExpirationTime, userIdClaim);
        } else if (tokenFlag.equals("refresh")) {
            return createToken(user.getNickname(), refreshTokenExpirationTime, userIdClaim);
        }

        throw new RuntimeException("Valid tokenFlag needed");
    }


    public void setAccessTokenToCookie(HttpServletResponse response, String accessToken) {

        Cookie jwtCookie = new Cookie("accessToken", accessToken);

        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(60 * 60);

        response.addCookie(jwtCookie);
    }

    public String regenerateAccessToken(String accessToken) {

        User foundUser = userRepository.findById(extractUserId(accessToken)).orElseThrow(EntityNotFoundException::new);

        String refreshToken = redisService.getRefreshToken(foundUser);

        boolean isRefreshTokenValid = isTokenValid(refreshToken);

        if (isRefreshTokenValid) {
            return generateToken(foundUser, "access");
        }

        throw new RuntimeException("Token expired: Both Access & Refresh token expired. Please log in again.");
    }

    public boolean isTokenValid(String token) {

        return extractExpiration(token).after(new Date());
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token) // @WIP
                .getBody();
    }
}
