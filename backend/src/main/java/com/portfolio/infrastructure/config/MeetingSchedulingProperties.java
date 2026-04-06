package com.portfolio.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.meeting")
public class MeetingSchedulingProperties {

    private int rateLimitPerMinute = 30;
    private int minNoticeHours = 2;
    private int maxDaysAhead = 28;
    private int businessHourStart = 9;
    private int businessHourEnd = 18;
    /** IANA, p. ej. America/Santo_Domingo */
    private String timezone = "America/Santo_Domingo";
}
