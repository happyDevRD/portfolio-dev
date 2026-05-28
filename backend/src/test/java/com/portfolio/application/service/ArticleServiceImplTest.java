package com.portfolio.application.service;

import com.portfolio.core.domain.model.Article;
import com.portfolio.core.domain.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void getAllArticles_returnsSortedByPublishedAtDescending() {
        Article older = Article.builder().slug("older").publishedAt(LocalDate.of(2024, 1, 1)).build();
        Article newer = Article.builder().slug("newer").publishedAt(LocalDate.of(2024, 6, 1)).build();
        Article newest = Article.builder().slug("newest").publishedAt(LocalDate.of(2024, 12, 1)).build();
        when(articleRepository.findAll()).thenReturn(List.of(older, newest, newer));

        List<Article> result = articleService.getAllArticles();

        assertThat(result).extracting(Article::getSlug)
                .containsExactly("newest", "newer", "older");
    }

    @Test
    void getAllArticles_returnsEmptyList_whenNoArticles() {
        when(articleRepository.findAll()).thenReturn(List.of());

        assertThat(articleService.getAllArticles()).isEmpty();
    }

    @Test
    void getArticleBySlug_returnsArticle_whenFound() {
        Article article = Article.builder().slug("my-post").title("My Post").build();
        when(articleRepository.findBySlug("my-post")).thenReturn(Optional.of(article));

        Optional<Article> result = articleService.getArticleBySlug("my-post");

        assertThat(result).isPresent();
        assertThat(result.get().getSlug()).isEqualTo("my-post");
        assertThat(result.get().getTitle()).isEqualTo("My Post");
    }

    @Test
    void getArticleBySlug_returnsEmpty_whenNotFound() {
        when(articleRepository.findBySlug("nonexistent")).thenReturn(Optional.empty());

        assertThat(articleService.getArticleBySlug("nonexistent")).isEmpty();
    }

    @Test
    void getAllArticles_delegatesToRepository() {
        when(articleRepository.findAll()).thenReturn(List.of());

        articleService.getAllArticles();

        verify(articleRepository).findAll();
        verifyNoMoreInteractions(articleRepository);
    }
}
