package com.portfolio.infrastructure.security;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Establece autenticación con rol ADMIN cuando la cabecera X-API-Key coincide con
 * {@code app.security.admin-api-key}. Solo debe registrarse en la cadena cuando
 * {@code app.security.require-api-key-for-writes} es true.
 */
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "X-API-Key";

    private final String adminApiKey;

    public ApiKeyAuthenticationFilter(String adminApiKey) {
        this.adminApiKey = adminApiKey;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String provided = request.getHeader(HEADER_NAME);
        if (StringUtils.hasText(this.adminApiKey) && this.adminApiKey.equals(provided)) {
            var auth = new UsernamePasswordAuthenticationToken(
                    "api-key",
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
