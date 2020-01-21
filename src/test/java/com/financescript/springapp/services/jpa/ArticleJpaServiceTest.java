package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Member;
import com.financescript.springapp.dto.tools.ArticleConverter;
import com.financescript.springapp.repositories.ArticleRepository;
import com.financescript.springapp.services.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

class ArticleJpaServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @Mock
    ArticleConverter articleConverter;

    ArticleService articleService;

    private Article article1;

    private final Long ARTICLE_ID1 = 2L;

    List<Article> articles;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        articleService = new ArticleJpaService(articleRepository, articleConverter);
        article1 = new Article();
        article1.setId(ARTICLE_ID1);
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
        Article article2 = new Article();
        article2.setId(6L);
        article2.setMember(new Member());
        articleService.save(article2);
        verify(articleRepository, times(1)).save(any(Article.class));
        //verify(articleConverter, times(1)).convert(anyString());
    }

    @Test
    void delete_samePrincipal() {
        // given
        Principal mockPrincipal = Mockito.mock(Principal.class);

        //when
        when(mockPrincipal.getName()).thenReturn("USER1");

        articleService.secureDelete(article1, "USER1", mockPrincipal);
        verify(articleRepository, times(1)).delete(any(Article.class));
    }

    @Test
    void findAllByTitleLike() {
        String RANDOM_INPUT_STRING = "Random";
        articleService.findAllByTitleLike(RANDOM_INPUT_STRING);
        verify(articleRepository, times(1)).findAllByTitleLikeIgnoreCase(anyString());
    }
}