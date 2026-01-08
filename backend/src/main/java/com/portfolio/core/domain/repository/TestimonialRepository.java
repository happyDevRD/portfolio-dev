package com.portfolio.core.domain.repository;

import com.portfolio.core.domain.model.Testimonial;
import java.util.List;

public interface TestimonialRepository {
    List<Testimonial> findAll();

    Testimonial save(Testimonial testimonial);
}
