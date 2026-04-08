package com.portfolio.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.meeting")
public class MeetingSchedulingProperties {

    private int rateLimitPerMinute = 30;
    private int minNoticeHours = 2;
    private int maxDaysAhead = 28;
    private int morningHourStart = 9;
    private int morningHourEnd = 12;
    private int afternoonHourStart = 14;
    private int afternoonHourEnd = 17;
    /** Tamaño de bloque para agendar (minutos). */
    private int slotMinutes = 60;
    /** TTL en segundos para caché de disponibilidad real. */
    private int availabilityCacheSeconds = 45;
    /** IANA, p. ej. America/Santo_Domingo */
    private String timezone = "America/Santo_Domingo";
}
