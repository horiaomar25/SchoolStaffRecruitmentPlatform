package com.example.SchoolStaffRecrutimentPlatform.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// This is designed to generate, validate and extract from JSON Web Tokens
// Component - Spring will create a instance and allow it be injected into other parts of the application.
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    // Initialize the secret key using a secure key generation method
    public JwtUtil() {

        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // key is used to sign and verify tokens
    }

    // Generate token for username registered and passes it onto doGenerateToken method
    // Username is stored inside the token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    // constructs JWT token using claims(username), issue date of token and expiration(valid for 1hr).
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Set additional data
                .setSubject(subject) // Store username and password
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token creation time
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiration
                .signWith(secretKey) // Sign with secret key
                .compact(); // converts into string format to be sent to client
    }

    // Verifies Token validality within 1hr. After an 1hr it will expire and user will have to log in in again.
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token); // decodes and verifies the token
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