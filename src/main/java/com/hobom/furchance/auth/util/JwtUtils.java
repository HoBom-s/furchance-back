package com.hobom.furchance.auth.util;

import com.hobom.furchance.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token-exp}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.refresh-token-exp}")
    private Long refreshTokenExpirationTime;

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
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Long> createUserIdClaim(User user) {

        Map<String, Long> userIdClaim = new HashMap<>();

        userIdClaim.put("userId", user.getId());

        return userIdClaim;
    }

    public String generateAccessToken(User user) {

        Map<String, Long> userIdClaim = createUserIdClaim(user);

        return createToken(user.getNickname(), accessTokenExpirationTime, userIdClaim);
    }

    public String generateRefreshToken(User user) {

        Map<String, Long> userIdClaim = createUserIdClaim(user);

        return createToken(user.getNickname(), refreshTokenExpirationTime, userIdClaim);
    }

    public String createRedisKey(User user) {
        return serviceName + "_" + user.getId() + "_" + user.getNickname();
    }

    public void setTokenToCookie(HttpServletResponse response, String accessToken) {

        Cookie jwtCookie = new Cookie("accessToken", accessToken);

        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(60 * 60);

        response.addCookie(jwtCookie);
    }

    public String extractAccessToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header format");
        }

        String accessToken = authHeader.substring(7);

        if (!isValidToken(accessToken)) {
            // @Todo refreshToken 더블체크 후 조치
            throw new RuntimeException("Invalid token");
        }

        return accessToken;
    }

    public boolean isValidToken(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
