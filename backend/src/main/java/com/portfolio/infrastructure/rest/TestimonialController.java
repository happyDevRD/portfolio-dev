package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Testimonial;
import com.portfolio.core.usecase.GetTestimonialsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
@Tag(name = "Testimonials", description = "Testimonios")
@RequiredArgsConstructor
public class TestimonialController {

    private final GetTestimonialsUseCase getTestimonialsUseCase;

    @GetMapping
    @Operation(summary = "Listar testimonios")
    public ResponseEntity<List<Testimonial>> getAllTestimonials() {
        return ResponseEntity.ok(getTestimonialsUseCase.execute());
    }
}
