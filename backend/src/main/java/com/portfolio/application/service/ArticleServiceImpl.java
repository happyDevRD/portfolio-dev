package com.portfolio.application.service;

import com.portfolio.core.domain.model.Article;
import com.portfolio.core.domain.repository.ArticleRepository;
import com.portfolio.core.usecase.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll().stream()
                .sorted(Comparator.comparing(Article::getPublishedAt).reversed())
                .toList();
    }

    @Override
    public Optional<Article> getArticleBySlug(String slug) {
        return articleRepository.findBySlug(slug);
    }
}
