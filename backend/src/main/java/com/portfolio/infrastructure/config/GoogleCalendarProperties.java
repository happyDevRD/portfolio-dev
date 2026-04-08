package com.portfolio.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.google.calendar")
public class GoogleCalendarProperties {

    private boolean enabled = false;
    private String applicationName = "portfolio-backend";
    /**
     * ID del calendario de destino ({@code primary}, correo del calendario o recurso compartido con la identidad ADC).
     * En prod/local se suele fijar con {@code GOOGLE_CALENDAR_ID} en el entorno (ver {@code application.yml}).
     */
    private String calendarId = "primary";

    /**
     * ID de cliente OAuth 2.0 (tipo &quot;Web application&quot;) de Google Cloud.
     * Útil si en el futuro se usa flujo OAuth + refresh token en lugar de solo cuenta de servicio.
     * Configurar con variable de entorno {@code GOOGLE_OAUTH_CLIENT_ID}; no commitear el valor.
     */
    private String oauth2ClientId = "";
}
