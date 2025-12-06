package com.portfolio.core.usecase;

import com.portfolio.core.domain.model.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> getAllArticles();

    Optional<Article> getArticleBySlug(String slug);
}
