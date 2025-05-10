package com.zanny.auth.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String secret = "my-secret-key-my-secret-key-my-secret-key-my-secret-key";

    private final long expirationMs = 3600000;

    // private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    // private final MacAlgorithm alg = Jwts.SIG.HS256;
    // private final SecretKey secretKey = alg.key().build(secret.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseEncryptedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
