package com.portfolio.core.domain.repository;

import com.portfolio.core.domain.model.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    List<Article> findAll();

    Optional<Article> findBySlug(String slug);

    Article save(Article article);
}
