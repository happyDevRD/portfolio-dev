package com.portfolio.infrastructure.config;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins:*}")
    private String allowedOrigins;

    @Override
    @SuppressWarnings("null") 
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        boolean isWildcard = "*".equals(allowedOrigins);

        var mapping = registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");

        if (isWildcard) {
            mapping.allowedOriginPatterns("*");
        } else {
            String raw = Objects.requireNonNull(allowedOrigins, "allowedOrigins must not be null");
            String[] origins = Arrays.stream(raw.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
            mapping.allowedOrigins(origins)
                   .allowCredentials(true);
        }
    }
}
