package com.portfolio.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.core.domain.model.Contact;
import com.portfolio.core.usecase.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactController.class)
@AutoConfigureMockMvc(addFilters = false)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContactService contactService;

    @Test
    void sendMessage_returns200_whenPayloadIsValid() throws Exception {
        Contact saved = Contact.builder().id(1L).name("Eleazar").email("dev@elgarcia.org").message("Hello").build();
        when(contactService.sendContactMessage(any(Contact.class))).thenReturn(saved);

        String body = """
                {
                    "name": "Eleazar",
                    "email": "dev@elgarcia.org",
                    "message": "Hello from test"
                }
                """;

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Mensaje enviado exitosamente"));
    }

    @Test
    void sendMessage_returns400_whenNameIsBlank() throws Exception {
        String body = """
                {
                    "name": "",
                    "email": "dev@elgarcia.org",
                    "message": "Hello"
                }
                """;

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").exists());
    }

    @Test
    void sendMessage_returns400_whenNameIsMissing() throws Exception {
        String body = """
                {
                    "email": "dev@elgarcia.org",
                    "message": "Hello"
                }
                """;

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").exists());
    }

    @Test
    void sendMessage_returns400_whenEmailIsInvalid() throws Exception {
        String body = """
                {
                    "name": "Eleazar",
                    "email": "not-an-email",
                    "message": "Hello"
                }
                """;

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email").exists());
    }

    @Test
    void sendMessage_returns400_whenEmailIsBlank() throws Exception {
        String body = """
                {
                    "name": "Eleazar",
                    "email": "",
                    "message": "Hello"
                }
                """;

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email").exists());
    }

    @Test
    void sendMessage_returns400_whenMessageIsBlank() throws Exception {
        String body = """
                {
                    "name": "Eleazar",
                    "email": "dev@elgarcia.org",
                    "message": ""
                }
                """;

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.message").exists());
    }

    @Test
    void sendMessage_returns400_whenBodyIsEmpty() throws Exception {
        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }
}
