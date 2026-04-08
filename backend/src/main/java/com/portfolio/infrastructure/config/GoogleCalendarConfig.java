package com.portfolio.infrastructure.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class GoogleCalendarConfig {

    /**
     * ADC resuelve credenciales sin JSON en el repo:
     * <ul>
     *   <li>Local: tras {@code gcloud auth application-default login}</li>
     *   <li>Cloud Run: identidad de la cuenta de servicio del servicio</li>
     *   <li>Opcional: {@code GOOGLE_APPLICATION_CREDENTIALS}</li>
     * </ul>
     */
    @Bean
    @ConditionalOnProperty(prefix = "app.google.calendar", name = "enabled", havingValue = "true")
    public Calendar googleCalendar(GoogleCalendarProperties props) throws Exception {
        GoogleCredentials credentials =
                GoogleCredentials.getApplicationDefault()
                        .createScoped(Collections.singleton(CalendarScopes.CALENDAR));
        HttpCredentialsAdapter requestInitializer = new HttpCredentialsAdapter(credentials);
        return new Calendar.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        requestInitializer)
                .setApplicationName(props.getApplicationName())
                .build();
    }
}
