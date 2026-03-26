package com.portfolio.infrastructure.security;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Limita POST /api/contact por dirección IP (o X-Forwarded-For) por ventana de un minuto.
 */
public class ContactRateLimitFilter extends OncePerRequestFilter {

    private static final String CONTACT_PATH = "/api/contact";
    private static final long WINDOW_MS = 60_000L;

    private final ConcurrentHashMap<String, WindowCounter> buckets = new ConcurrentHashMap<>();

    private final int maxPerMinute;

    public ContactRateLimitFilter(int maxPerMinute) {
        this.maxPerMinute = maxPerMinute;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return !"POST".equalsIgnoreCase(request.getMethod()) || !CONTACT_PATH.equals(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String ip = clientIp(request);
        long now = System.currentTimeMillis();
        WindowCounter w = buckets.computeIfAbsent(ip, k -> new WindowCounter());

        synchronized (w) {
            if (now - w.windowStart >= WINDOW_MS) {
                w.windowStart = now;
                w.count = 0;
            }
            if (w.count >= maxPerMinute) {
                response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many contact requests");
                return;
            }
            w.count++;
        }

        filterChain.doFilter(request, response);
    }

    private static String clientIp(HttpServletRequest req) {
        String xff = req.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(xff)) {
            return xff.split(",")[0].trim();
        }
        return req.getRemoteAddr();
    }

    private static final class WindowCounter {
        long windowStart = System.currentTimeMillis();
        int count;
    }
}
