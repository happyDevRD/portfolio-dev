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

import java.io.IOException;
import java.util.Collections;

@Configuration
public class GoogleCalendarConfig {

    /**
     * Cliente Calendar con ADC:
     * <ul>
     *   <li>Local: credenciales de usuario tras {@code gcloud auth application-default login}</li>
     *   <li>Cloud Run / GCE: identidad de la cuenta de servicio del recurso</li>
     *   <li>Opcional: {@code GOOGLE_APPLICATION_CREDENTIALS} apuntando a un JSON de cuenta de servicio</li>
     * </ul>
     */
    @Bean
    @ConditionalOnProperty(prefix = "app.google.calendar", name = "enabled", havingValue = "true")
    public Calendar googleCalendar(GoogleCalendarProperties props) throws Exception {
        GoogleCredentials credentials = googleCalendarCredentials();
        HttpCredentialsAdapter requestInitializer = new HttpCredentialsAdapter(credentials);
        return new Calendar.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        requestInitializer)
                .setApplicationName(props.getApplicationName())
                .build();
    }

    private static GoogleCredentials googleCalendarCredentials() throws IOException {
        return GoogleCredentials.getApplicationDefault()
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));
    }
}
