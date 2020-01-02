package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Member;
import com.financescript.springapp.domains.util.LocalDateTimeWriter;
import com.financescript.springapp.services.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ArticleControllerTest {

    @Mock
    ArticleService articleService;

    @Mock
    LocalDateTimeWriter localDateTimeWriter;

    @Mock
    Model model;

    ArticleController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ArticleController(articleService, localDateTimeWriter);
    }

    @Test
    void showArticleList() {
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setId(1L);
        article.setMember(new Member());
        article.setCreationDateTime();
        articles.add(new Article());

        when(articleService.findAllByOrderByCreationTimeDesc()).thenReturn(articles);

        assertEquals("articles/article-list", controller.showArticleList(model));
    }
}