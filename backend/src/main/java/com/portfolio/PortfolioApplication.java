package com.portfolio;

import com.portfolio.infrastructure.config.GoogleCalendarProperties;
import com.portfolio.infrastructure.config.MeetingSchedulingProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
@EnableConfigurationProperties({ MeetingSchedulingProperties.class, GoogleCalendarProperties.class })
public class PortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}

}
