package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.repositories.ArticleRepository;
import com.financescript.springapp.services.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;


class ArticleJpaServiceTest {

    @Mock
    ArticleRepository articleRepository;

    ArticleService articleService;

    private Article article1;

    private final Long ARTICLE_ID1 = 2L;

    List<Article> articles;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        articleService = new ArticleJpaService(articleRepository);
        article1 = Article.builder().id(ARTICLE_ID1).build();
        articles = new ArrayList<>();
        articles.add(article1);
        when(articleRepository.findAll()).thenReturn(articles);
    }

    @Test
    void findAllByOrderByCreationTimeDesc() {
        articleService.findAllByOrderByCreationTimeDesc();
        verify(articleRepository, times(1)).findAllByOrderByCreationTimeDesc();
    }

    @Test
    void findById() {
        articleService.findById(ARTICLE_ID1);
        verify(articleRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        articleService.save(Article.builder().id(6L).build());
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void deleteById() {
        articleService.deleteById(ARTICLE_ID1);
        verify(articleRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void delete() {
        articleService.delete(article1);
        verify(articleRepository, times(1)).delete(any(Article.class));
    }

    @Test
    void findAllByTitleLike() {
        String RANDOM_INPUT_STRING = "Random";
        articleService.findAllByTitleLike(RANDOM_INPUT_STRING);
        verify(articleRepository, times(1)).findAllByTitleLike(anyString());
    }
}