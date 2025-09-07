package com.example.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public record Principal(Long userId, String username) {}

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        try {
            Jws<io.jsonwebtoken.Claims> jws = jwtService.parse(token);
            Claims claims = jws.getBody();

            Long userId = Long.valueOf(claims.getSubject());
            String username = (String) claims.get("username");
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) claims.get("roles");

            List<SimpleGrantedAuthority> authorities = Optional.ofNullable(roles)
                    .orElseGet(List::of)
                    .stream()
                    .map(r -> {
                        String withPrefix = r.startsWith("ROLE_") ? r : "ROLE_" + r.toUpperCase();
                        return new SimpleGrantedAuthority(withPrefix);
                    })
                    .toList();

            var principal = new Principal(userId, username);
            var auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception ex) {
            org.springframework.security.core.context.SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
