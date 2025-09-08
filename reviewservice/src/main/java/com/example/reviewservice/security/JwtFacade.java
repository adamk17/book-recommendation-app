package com.example.reviewservice.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("jwt")
public class JwtFacade {
    public Long extractUserId(Authentication auth) {
        if (auth == null) return null;
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails ud) {
            return Long.valueOf(ud.getUsername()); // u nas username == userId (z JwtAuthFilter)
        }
        if (principal instanceof String s) {
            return Long.valueOf(s);
        }
        return null;
    }

    public boolean isSelf(Long userId, Authentication auth) {
        Long me = extractUserId(auth);
        return me != null && me.equals(userId);
    }
}

