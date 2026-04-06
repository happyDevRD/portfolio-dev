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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Collections;

@Configuration
public class GoogleCalendarConfig {

    @Bean
    @ConditionalOnProperty(prefix = "app.google.calendar", name = "enabled", havingValue = "true")
    public Calendar googleCalendar(GoogleCalendarProperties props, ResourceLoader resourceLoader) throws Exception {
        if (!StringUtils.hasText(props.getCredentialsPath())) {
            throw new IllegalStateException(
                    "app.google.calendar.credentials-path debe estar definido cuando enabled=true");
        }
        Resource resource = resourceLoader.getResource(props.getCredentialsPath());
        try (InputStream in = resource.getInputStream()) {
            GoogleCredentials credentials =
                    GoogleCredentials.fromStream(in).createScoped(Collections.singleton(CalendarScopes.CALENDAR));
            HttpCredentialsAdapter requestInitializer = new HttpCredentialsAdapter(credentials);
            return new Calendar.Builder(
                            GoogleNetHttpTransport.newTrustedTransport(),
                            GsonFactory.getDefaultInstance(),
                            requestInitializer)
                    .setApplicationName(props.getApplicationName())
                    .build();
        }
    }
}
