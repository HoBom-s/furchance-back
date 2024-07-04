package com.hobom.furchance.user.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token.exp}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token.exp}")
    private Long refreshTokenExpiration;

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String createToken(String subject, long expirationTime, Map<String, String> userIdClaim) {
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(userIdClaim)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(String nickname, Map<String, String> userIdClaim) {
        return createToken(nickname, accessTokenExpiration, userIdClaim);
    }

    public String generateRefreshToken(String nickname, Long userId, Map<String, String> userIdClaim) {
        return createToken(nickname, refreshTokenExpiration, userIdClaim);
    }

}
