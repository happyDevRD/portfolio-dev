package com.portfolio.infrastructure.config;

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
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        boolean isWildcard = "*".equals(allowedOrigins);

        var mapping = registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");

        if (isWildcard) {
            mapping.allowedOriginPatterns("*");
        } else {
            String[] origins = Objects.requireNonNull(allowedOrigins, "allowedOrigins must not be null")
                                      .split(",");
            mapping.allowedOrigins(origins)
                   .allowCredentials(true);
        }
    }
}
