package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Testimonial;
import com.portfolio.core.usecase.GetTestimonialsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
@RequiredArgsConstructor

public class TestimonialController {

    private final GetTestimonialsUseCase getTestimonialsUseCase;

    @GetMapping
    public ResponseEntity<List<Testimonial>> getAllTestimonials() {
        return ResponseEntity.ok(getTestimonialsUseCase.execute());
    }
}
