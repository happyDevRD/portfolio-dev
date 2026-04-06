package com.portfolio.infrastructure.persistence;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Arranque real con PostgreSQL + Flyway + Hibernate validate.
 * Requiere Docker local o en CI; si no hay Docker, JUnit omite la clase.
 */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FlywayPostgresIntegrationTest extends PostgresIntegrationTestBase {

    @DynamicPropertySource
    static void registerSecurityForFlywayTest(DynamicPropertyRegistry registry) {
        registry.add("app.security.require-api-key-for-writes", () -> "false");
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void flywayApplied_andPublicApiResponds() throws Exception {
        mockMvc.perform(get("/api/projects")).andExpect(status().isOk());
    }
}
