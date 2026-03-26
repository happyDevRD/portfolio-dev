package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Contact;
import com.portfolio.core.usecase.ContactService;
import com.portfolio.infrastructure.rest.dto.ContactRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@Tag(name = "Contact", description = "Formulario de contacto")
@RequiredArgsConstructor
@Slf4j
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @Operation(summary = "Enviar mensaje de contacto")
    public ResponseEntity<Map<String, String>> sendMessage(@Valid @RequestBody ContactRequest request) {
        Contact contact = Contact.builder()
                .name(request.getName())
                .email(request.getEmail())
                .message(request.getMessage())
                .build();

        contactService.sendContactMessage(contact);
        log.info("Saved contact message from: {}", request.getEmail());

        return ResponseEntity.ok(Collections.singletonMap("message", "Mensaje enviado exitosamente"));
    }
}
