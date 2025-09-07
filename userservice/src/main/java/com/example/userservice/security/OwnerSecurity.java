package com.example.userservice.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("ownerSecurity")
public class OwnerSecurity {

    public boolean isOwner(Long pathUserId, Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) return false;
        if (authentication.getPrincipal() instanceof JwtAuthFilter.Principal p) {
            return p.userId().equals(pathUserId);
        }
        return false;
    }
}
