package com.ehr_integration_platform.security;

import com.ehr_integration_platform.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Minimum 32 chars required
    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey";

    // 1 hour
    private static final long EXPIRATION =
            1000 * 60 * 60;

    // Generate Security Key
    private Key getKey() {

        return Keys.hmacShaKeyFor(
                SECRET.getBytes()
        );
    }

    // GENERATE JWT TOKEN
    public String generateToken(String username,Role role)
    {
        Map<String, Object> claims =
                new HashMap<>();

        claims.put("role", role.name());

        return Jwts.builder()

                .setClaims(claims)

                .setSubject(username)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION
                        )
                )

                .signWith(
                        getKey(),
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    // EXTRACT USERNAME
    public String extractUsername(String token) {

        return extractClaims(token)
                .getSubject();
    }

    // EXTRACT ROLE
    public String extractRole(String token) {

        return extractClaims(token)
                .get("role", String.class);
    }

    // EXTRACT ALL CLAIMS
    private Claims extractClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(getKey())

                .build()

                .parseClaimsJws(token)

                .getBody();
    }

    // VALIDATE TOKEN
    public boolean validateToken(String token) {

        try {

            extractClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}