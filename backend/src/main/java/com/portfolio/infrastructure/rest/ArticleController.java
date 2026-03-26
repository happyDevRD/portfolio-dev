package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Article;
import com.portfolio.core.usecase.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "Articles", description = "Artículos del blog")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    @Operation(summary = "Listar artículos")
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Obtener artículo por slug")
    public ResponseEntity<Article> getArticleBySlug(@PathVariable String slug) {
        return articleService.getArticleBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
