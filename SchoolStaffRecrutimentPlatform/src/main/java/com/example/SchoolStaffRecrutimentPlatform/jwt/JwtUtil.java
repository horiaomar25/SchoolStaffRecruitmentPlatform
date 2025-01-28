package com.example.SchoolStaffRecrutimentPlatform.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// This is designed to generate, validate and parse JSON Web Tokens
// Component - Spring will create a instance and allow it be injected into other parts of the application.
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    // Initialize the secret key using a secure key generation method
    public JwtUtil() {

        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // key is used to sign and verify tokens
    }

    // Generate token for email registered and passes it onto doGenerateToken method
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, email);
    }

    // constructs JWT token using claims(email), issue date of token and expiration(valid for 1hr).
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiration
                .signWith(secretKey)
                .compact();
    }

    // Verifies Token validility within 1hr. After an 1hr it will expire and user will have to login in again.
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Username extracted from token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}