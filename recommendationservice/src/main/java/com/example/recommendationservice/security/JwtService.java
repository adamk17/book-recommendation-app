package com.example.recommendationservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Service
public class JwtService {

    private final Key key;

    public JwtService(@Value("${security.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    @SuppressWarnings("unchecked")
    public Collection<String> extractRoles(Claims claims) {
        Object roles = claims.get("roles");
        if (roles instanceof List<?> list) {
            return list.stream().map(String::valueOf).toList();
        }
        return List.of();
    }

    public String extractSubject(Claims claims) {
        return claims.getSubject();
    }
}
