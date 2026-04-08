package com.portfolio.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Ayuda a detectar despliegues donde la agenda queda desactivada por configuración.
 */
@Component
public class GoogleCalendarStartupLogger {

    private static final Logger log = LoggerFactory.getLogger(GoogleCalendarStartupLogger.class);

    @EventListener(ApplicationReadyEvent.class)
    public void onReady(ApplicationReadyEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();
        if (!env.matchesProfiles("prod")) {
            return;
        }
        Boolean enabled = env.getProperty("app.google.calendar.enabled", Boolean.class, Boolean.FALSE);
        if (!Boolean.TRUE.equals(enabled)) {
            log.warn(
                    "Google Calendar está desactivado (app.google.calendar.enabled=false). "
                            + "Para activar la agenda en Cloud Run, define la variable de entorno GOOGLE_CALENDAR_ENABLED=true "
                            + "(p. ej. secret en GitHub Actions) y redepliega.");
        }
    }
}
