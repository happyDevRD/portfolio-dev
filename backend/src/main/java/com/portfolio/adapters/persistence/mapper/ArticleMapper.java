package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.ArticleEntity;
import com.portfolio.core.domain.model.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ArticleMapper {

    public Article toDomain(ArticleEntity entity) {
        if (entity == null)
            return null;
        return Article.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .slug(entity.getSlug())
                .summary(entity.getSummary())
                .content(entity.getContent())
                .coverImageUrl(entity.getCoverImageUrl())
                .tags(entity.getTags() == null ? null : new ArrayList<>(entity.getTags()))
                .publishedAt(entity.getPublishedAt())
                .readingTimeMinutes(entity.getReadingTimeMinutes())
                .build();
    }

    public ArticleEntity toEntity(Article domain) {
        if (domain == null)
            return null;
        return ArticleEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .slug(domain.getSlug())
                .summary(domain.getSummary())
                .content(domain.getContent())
                .coverImageUrl(domain.getCoverImageUrl())
                .tags(domain.getTags())
                .publishedAt(domain.getPublishedAt())
                .readingTimeMinutes(domain.getReadingTimeMinutes())
                .build();
    }
}
