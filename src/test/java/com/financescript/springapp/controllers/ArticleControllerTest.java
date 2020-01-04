package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Member;
import com.financescript.springapp.domains.util.LocalDateTimeWriter;
import com.financescript.springapp.services.ArticleService;
import com.financescript.springapp.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchers.*;

class ArticleControllerTest {

    @Mock
    ArticleService articleService;

    @Mock
    LocalDateTimeWriter localDateTimeWriter;

    @Mock
    MemberService memberService;

    @Mock
    Model model;

    ArticleController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ArticleController(articleService, memberService, localDateTimeWriter);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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

    @Test
    void newRecipe_noPrincipal() throws Exception {
        // given
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/new");

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    void newRecipe_existingPrincipal() throws Exception {
        // given
        Principal mockPrincipal = Mockito.mock(Principal.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/new")
                .principal(mockPrincipal);

        // when
        when(mockPrincipal.getName()).thenReturn("USER1");

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("articles/form"));
    }

    @Test
    void saveOrUpdate_noPrincipal() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        article1.setMember(new Member());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/articles/new")
                .requestAttr("article", article1);
        // not setting principal so that the principal becomes null

        // when
        when(articleService.save(any(Article.class))).thenReturn(article1);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alertMessage"))
                .andExpect(view().name("security/login"));
    }

    @Test
    void saveOrUpdate_existingPrincipal() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        article1.setMember(new Member());
        Principal mockPrincipal = Mockito.mock(Principal.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/articles/new")
                .principal(mockPrincipal)
                .requestAttr("article", article1);

        // when
        when(mockPrincipal.getName()).thenReturn("USER1");
        when(articleService.save(any(Article.class))).thenReturn(article1);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/1/show"));
    }

    @Test
    void saveOrUpdate_hasBindingError() throws Exception {
        // given
        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);

        Article article1 = new Article();
        article1.setId(1L);
        article1.setMember(new Member());
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Model model = Mockito.mock(Model.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/articles/new")
                .principal(mockPrincipal)
                .requestAttr("article", article1);

        // when
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);

        // then
        assertEquals("articles/form", controller.saveOrUpdate(article1, model, mockBindingResult, mockPrincipal));
    }

    @Test
    void showArticle_noPrincipal() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        article1.setMember(new Member());

        // when
        when(articleService.findById(any())).thenReturn(article1);

        // then
        mockMvc.perform(get("/articles/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/show")); // need to edit
    }

    @Test
    void showArticle_existingPrincipal() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        article1.setMember(new Member());
        Principal mockPrincipal = Mockito.mock(Principal.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/1/show")
                .principal(mockPrincipal)
                .requestAttr("article", article1);

        // when
        when(articleService.findById(any())).thenReturn(article1);
        when(mockPrincipal.getName()).thenReturn("USER1");

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sessionUser"))
                .andExpect(view().name("articles/show"));
    }

    @Test
    void updateArticle_nullArticle() throws Exception {
        // given
        // when
        when(articleService.findById(any())).thenReturn(null);

        // then
        mockMvc.perform(get("/articles/1/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles")); // consider throwing 404 error
    }

    @Test
    void updateArticle_noPrincipal() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        article1.setMember(new Member());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/1/update")
                .requestAttr("article", article1);

        // when
        when(articleService.findById(any())).thenReturn(article1);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/1/show")); // consider throwing 404 error
    }

    @Test
    void updateArticle_author() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Member member = new Member();
        member.setUsername("USER1");
        article1.setMember(member);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/1/update")
                .principal(mockPrincipal)
                .requestAttr("article", article1);

        // when
        when(articleService.findById(any())).thenReturn(article1);
        when(mockPrincipal.getName()).thenReturn("USER1");

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("articles/form")); // consider throwing 404 error
    }

}