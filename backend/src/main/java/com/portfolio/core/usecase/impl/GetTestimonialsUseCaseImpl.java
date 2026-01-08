package com.portfolio.core.usecase.impl;

import com.portfolio.core.domain.model.Testimonial;
import com.portfolio.core.domain.repository.TestimonialRepository;
import com.portfolio.core.usecase.GetTestimonialsUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class GetTestimonialsUseCaseImpl implements GetTestimonialsUseCase {
    private final TestimonialRepository testimonialRepository;

    @Override
    public List<Testimonial> execute() {
        return testimonialRepository.findAll();
    }

    @Override
    public Testimonial createTestimonial(Testimonial testimonial) {
        return testimonialRepository.save(testimonial);
    }
}
