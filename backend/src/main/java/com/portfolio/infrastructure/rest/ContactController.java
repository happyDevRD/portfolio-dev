package com.portfolio.infrastructure.rest;

import com.portfolio.infrastructure.rest.dto.ContactRequest;
import jakarta.validation.Valid;
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
@Slf4j
public class ContactController {

    private final com.portfolio.core.usecase.ContactService contactService;

    public ContactController(com.portfolio.core.usecase.ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> sendMessage(@Valid @RequestBody ContactRequest request) {
        com.portfolio.core.domain.model.Contact contact = com.portfolio.core.domain.model.Contact.builder()
                .name(request.getName())
                .email(request.getEmail())
                .message(request.getMessage())
                .build();

        contactService.sendContactMessage(contact);
        log.info("Saved contact message from: {}", request.getEmail());

        return ResponseEntity.ok(Collections.singletonMap("message", "Message sent successfully"));
    }
}
