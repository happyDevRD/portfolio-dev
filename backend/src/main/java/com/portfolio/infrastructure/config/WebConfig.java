package com.portfolio.infrastructure.config;

import org.springframework.context.annotation.Configuration; // Correct import
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Explicit origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "Accept", "Origin",
                        "Access-Control-Request-Method", "Access-Control-Request-Headers")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
