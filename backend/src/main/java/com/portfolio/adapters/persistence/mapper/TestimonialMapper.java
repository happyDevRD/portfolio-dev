package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.TestimonialEntity;
import com.portfolio.core.domain.model.Testimonial;
import org.springframework.stereotype.Component;

@Component
public class TestimonialMapper {

    public Testimonial toDomain(TestimonialEntity entity) {
        if (entity == null) {
            return null;
        }
        return Testimonial.builder()
                .id(entity.getId())
                .authorName(entity.getAuthorName())
                .authorRole(entity.getAuthorRole())
                .content(entity.getContent())
                .avatarUrl(entity.getAvatarUrl())
                .build();
    }

    public TestimonialEntity toEntity(Testimonial domain) {
        if (domain == null) {
            return null;
        }
        return TestimonialEntity.builder()
                .id(domain.getId())
                .authorName(domain.getAuthorName())
                .authorRole(domain.getAuthorRole())
                .content(domain.getContent())
                .avatarUrl(domain.getAvatarUrl())
                .build();
    }
}
