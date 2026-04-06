package com.portfolio.infrastructure.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.portfolio.infrastructure.persistence.PostgresIntegrationTestBase;

@SpringBootTest
@AutoConfigureMockMvc
class ApiSecurityIntegrationTest extends PostgresIntegrationTestBase {

    private static final String MINIMAL_PROJECT_JSON = """
            {
              "title": "Test",
              "description": "Desc",
              "imageUrl": "",
              "projectUrl": "",
              "githubUrl": "",
              "startDate": "2024-01-01",
              "tags": [],
              "challenge": "",
              "solution": "",
              "features": []
            }
            """;

    @Nested
    @DisplayName("Modo desarrollo (sin API key obligatoria)")
    class DevMode {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void getProjects_isOk() throws Exception {
            mockMvc.perform(get("/api/projects")).andExpect(status().isOk());
        }
    }

    @Nested
    @SpringBootTest
    @AutoConfigureMockMvc
    @TestPropertySource(
            properties = {
                "app.security.require-api-key-for-writes=true",
                "app.security.admin-api-key=test-secret",
                "app.contact.rate-limit-per-minute=10000",
                "app.meeting.rate-limit-per-minute=10000"
            })
    @DisplayName("Escrituras protegidas por API key")
    class SecuredWrites extends PostgresIntegrationTestBase {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void postProject_withoutKey_returnsForbidden() throws Exception {
            mockMvc.perform(
                            post("/api/projects")
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .content(MINIMAL_PROJECT_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        void postProject_withValidKey_returnsCreated() throws Exception {
            mockMvc.perform(
                            post("/api/projects")
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .header(ApiKeyAuthenticationFilter.HEADER_NAME, "test-secret")
                                    .content(MINIMAL_PROJECT_JSON))
                    .andExpect(status().isCreated());
        }

        @Test
        void postProject_invalidDto_returnsBadRequest() throws Exception {
            mockMvc.perform(
                            post("/api/projects")
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                                    .header(ApiKeyAuthenticationFilter.HEADER_NAME, "test-secret")
                                    .content("{\"title\":\"\"}"))
                    .andExpect(status().isBadRequest());
        }
    }
}
