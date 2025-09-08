package com.example.recommendationservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FeignAuthConfig {

    @Bean
    public RequestInterceptor authForwardingInterceptor() {
        return template -> {
            var attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                String auth = attrs.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
                if (StringUtils.hasText(auth)) {
                    template.header(HttpHeaders.AUTHORIZATION, auth);
                }
            }
        };
    }
}
