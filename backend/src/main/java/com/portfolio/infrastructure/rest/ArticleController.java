package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Article;
import com.portfolio.core.usecase.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor

public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Article> getArticleBySlug(@PathVariable String slug) {
        return articleService.getArticleBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
