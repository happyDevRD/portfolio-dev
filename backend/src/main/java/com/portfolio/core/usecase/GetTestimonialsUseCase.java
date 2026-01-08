package com.portfolio.core.usecase;

import com.portfolio.core.domain.model.Testimonial;
import java.util.List;

public interface GetTestimonialsUseCase {
    List<Testimonial> execute();

    Testimonial createTestimonial(Testimonial testimonial);
}
