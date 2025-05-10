package com.zanny.auth.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String secret = "my-secret-key-my-secret-key-my-secret-key-my-secret-key";

    private final long expirationMs = 3600000;

    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

}
