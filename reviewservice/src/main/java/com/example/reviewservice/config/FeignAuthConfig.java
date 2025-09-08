package com.example.reviewservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignAuthConfig {
    @Bean
    public RequestInterceptor bearerTokenRequestInterceptor() {
        return template -> {
            var ctx = SecurityContextHolder.getContext();
            var auth = ctx != null ? ctx.getAuthentication() : null;
            if (auth != null && auth.getCredentials() instanceof String token && !token.isBlank()) {
                template.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            }
        };
    }
}
