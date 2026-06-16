package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Article;
import com.portfolio.core.usecase.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@AutoConfigureMockMvc(addFilters = false)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    void getAllArticles_returns200WithList() throws Exception {
        List<Article> articles = List.of(
                Article.builder().id(1L).slug("post-one").title("Post One").publishedAt(LocalDate.of(2024, 6, 1)).build(),
                Article.builder().id(2L).slug("post-two").title("Post Two").publishedAt(LocalDate.of(2024, 1, 1)).build()
        );
        when(articleService.getAllArticles()).thenReturn(articles);

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].slug").value("post-one"))
                .andExpect(jsonPath("$[1].slug").value("post-two"));
    }

    @Test
    void getAllArticles_returns200WithEmptyList_whenNoArticles() throws Exception {
        when(articleService.getAllArticles()).thenReturn(List.of());

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getArticleBySlug_returns200_whenFound() throws Exception {
        Article article = Article.builder()
                .id(1L)
                .slug("clean-architecture-guide")
                .title("Clean Architecture Guide")
                .summary("How to apply Clean Architecture in Spring Boot")
                .publishedAt(LocalDate.of(2024, 3, 15))
                .readingTimeMinutes(8)
                .build();
        when(articleService.getArticleBySlug("clean-architecture-guide")).thenReturn(Optional.of(article));

        mockMvc.perform(get("/api/articles/clean-architecture-guide"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("clean-architecture-guide"))
                .andExpect(jsonPath("$.title").value("Clean Architecture Guide"))
                .andExpect(jsonPath("$.readingTimeMinutes").value(8));
    }

    @Test
    void getArticleBySlug_returns404_whenNotFound() throws Exception {
        when(articleService.getArticleBySlug("nonexistent-slug")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/articles/nonexistent-slug"))
                .andExpect(status().isNotFound());
    }
}
