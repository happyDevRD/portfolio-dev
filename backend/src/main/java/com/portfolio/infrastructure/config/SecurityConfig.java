package com.portfolio.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.portfolio.infrastructure.security.ApiKeyAuthenticationFilter;
import com.portfolio.infrastructure.security.ContactRateLimitFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public ContactRateLimitFilter contactRateLimitFilter(
            @Value("${app.contact.rate-limit-per-minute:30}") int maxPerMinute) {
        return new ContactRateLimitFilter(maxPerMinute);
    }

    @Bean
    public ApiKeyAuthenticationFilter apiKeyAuthenticationFilter(
            @Value("${app.security.admin-api-key:}") String adminApiKey) {
        return new ApiKeyAuthenticationFilter(adminApiKey);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            ContactRateLimitFilter contactRateLimitFilter,
            ApiKeyAuthenticationFilter apiKeyAuthenticationFilter,
            @Value("${app.security.require-api-key-for-writes:false}") boolean requireApiKeyForWrites,
            @Value("${app.security.admin-api-key:}") String adminApiKey) throws Exception {

        if (requireApiKeyForWrites && !StringUtils.hasText(adminApiKey)) {
            throw new IllegalStateException(
                    "ADMIN_API_KEY must be set when app.security.require-api-key-for-writes=true");
        }

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(contactRateLimitFilter, UsernamePasswordAuthenticationFilter.class);
        if (requireApiKeyForWrites) {
            http.addFilterBefore(apiKeyAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        }

        if (!requireApiKeyForWrites) {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/contact").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .anyRequest().denyAll());

        return http.build();
    }
}
