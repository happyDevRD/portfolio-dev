package com.portfolio.adapters.persistence;

import com.portfolio.adapters.persistence.mapper.ArticleMapper;
import com.portfolio.adapters.persistence.repository.JpaArticleRepository;
import com.portfolio.core.domain.model.Article;
import com.portfolio.core.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArticlePersistenceAdapter implements ArticleRepository {

    private final JpaArticleRepository jpaRepository;
    private final ArticleMapper mapper;

    @Override
    public List<Article> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Article> findBySlug(String slug) {
        return jpaRepository.findBySlug(slug)
                .map(mapper::toDomain);
    }

    @Override
    public Article save(Article article) {
        return mapper.toDomain(
                jpaRepository.save(mapper.toEntity(article)));
    }
}
