package com.portfolio.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.google.calendar")
public class GoogleCalendarProperties {

    private boolean enabled = false;
    private String applicationName = "portfolio-backend";
    /** ID del calendario (p. ej. primary o correo del calendario compartido con la cuenta de servicio). */
    private String calendarId = "primary";
    /**
     * Ruta Spring Resource: file:/ruta/creds.json o classpath:creds.json
     */
    private String credentialsPath = "";

    /**
     * ID de cliente OAuth 2.0 (tipo &quot;Web application&quot;) de Google Cloud.
     * Útil si en el futuro se usa flujo OAuth + refresh token en lugar de solo cuenta de servicio.
     * Configurar con variable de entorno {@code GOOGLE_OAUTH_CLIENT_ID}; no commitear el valor.
     */
    private String oauth2ClientId = "";
}
