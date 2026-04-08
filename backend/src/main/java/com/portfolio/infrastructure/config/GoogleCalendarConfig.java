package com.portfolio.infrastructure.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class GoogleCalendarConfig {

    /**
     * Credenciales de aplicación por defecto (ADC): Workload Identity en GCP, variable
     * {@code GOOGLE_APPLICATION_CREDENTIALS}, o metadatos de la VM.
     */
    @Bean(name = "googleCalendarCredentials")
    @ConditionalOnProperty(prefix = "app.google.calendar", name = "enabled", havingValue = "true")
    public GoogleCredentials googleCalendarCredentials() throws IOException {
        return GoogleCredentials.getApplicationDefault()
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.google.calendar", name = "enabled", havingValue = "true")
    public Calendar googleCalendar(
            GoogleCalendarProperties props,
            @Qualifier("googleCalendarCredentials") GoogleCredentials googleCalendarCredentials)
            throws Exception {
        HttpCredentialsAdapter requestInitializer = new HttpCredentialsAdapter(googleCalendarCredentials);
        return new Calendar.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        requestInitializer)
                .setApplicationName(props.getApplicationName())
                .build();
    }
}
