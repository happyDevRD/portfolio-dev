package com.portfolio.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI portfolioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Portfolio API")
                        .version("1.0")
                        .description(
                                "API del portafolio. En producción, las mutaciones (POST/PUT/DELETE) requieren la cabecera X-API-Key."))
                .components(new Components().addSecuritySchemes("X-API-Key",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-API-Key")
                                .description("Clave de administración (ADMIN_API_KEY) cuando está activada la protección de escrituras.")));
    }
}
