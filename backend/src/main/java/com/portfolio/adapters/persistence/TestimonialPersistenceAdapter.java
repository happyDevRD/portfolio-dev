package com.portfolio.adapters.persistence;

import com.portfolio.adapters.persistence.entity.TestimonialEntity;
import com.portfolio.adapters.persistence.mapper.TestimonialMapper;
import com.portfolio.adapters.persistence.repository.TestimonialJpaRepository;
import com.portfolio.core.domain.model.Testimonial;
import com.portfolio.core.domain.repository.TestimonialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TestimonialPersistenceAdapter implements TestimonialRepository {
    private final TestimonialJpaRepository jpaRepository;
    private final TestimonialMapper mapper;

    @Override
    public List<Testimonial> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Testimonial save(Testimonial testimonial) {
        TestimonialEntity entity = mapper.toEntity(testimonial);
        if (entity == null) {
            throw new IllegalArgumentException("Testimonial cannot be null");
        }
        TestimonialEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
}
