package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.domains.Member;
import com.financescript.springapp.domains.util.LocalDateTimeWriter;
import com.financescript.springapp.services.ArticleService;
import com.financescript.springapp.services.CommentService;
import com.financescript.springapp.services.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ArticleControllerTest {

    @Mock
    ArticleService articleService;

    @Mock
    LocalDateTimeWriter localDateTimeWriter;

    @Mock
    MemberService memberService;

    @Mock
    CommentService commentService;

    @Mock
    Model model;

    ArticleController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ArticleController(articleService, memberService, localDateTimeWriter, commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ExceptionController()).build();
    }

    @Test
    void showArticleList__nullArticle() {
        List<Article> articles = new ArrayList<>();

        when(articleService.findAllByOrderByCreationTimeDesc()).thenReturn(articles);

        assertEquals("articles/article-list", controller.showArticleList(null, model));
        verify(articleService, times(1)).findAllByOrderByCreationTimeDesc();
    }

    @Test
    void showArticleList__articleWithUnknownTitle() {
        List<Article> articles = new ArrayList<>();
        Article article = new Article();

        when(articleService.findAllByOrderByCreationTimeDesc()).thenReturn(articles);

        assertEquals("articles/article-list", controller.showArticleList(article, model));
        verify(articleService, times(1)).findAllByOrderByCreationTimeDesc();
    }

    @Test
    void showArticleList__givenArticleWithTitle() {
        List<Article> articles = new ArrayList<>();
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Something");
        article.setMember(new Member());
        article.setCreationDateTime();
        articles.add(new Article());

        when(articleService.findAllByOrderByCreationTimeDesc()).thenReturn(articles);

        assertEquals("articles/article-list", controller.showArticleList(article, model));
        verify(articleService, times(1)).findAllByTitleLike(anyString());
    }

    @Test
    void newArticle_noPrincipal() throws Exception {
        // given
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/new");

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    void newArticle_existingPrincipal() throws Exception {
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
        article1.setOriginalText("Something");
        article1.setTitle("Something Appropriate");
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
        article1.setOriginalText("Something");
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
    void saveOrUpdate_hasBindingError() {
        // given
        BindingResult mockBindingResult = Mockito.mock(BindingResult.class);

        Article article1 = new Article();
        article1.setId(1L);
        article1.setMember(new Member());
        article1.setOriginalText("Something");
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Model model = Mockito.mock(Model.class);

        // when
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);

        // then
        assertEquals("articles/form", controller.saveOrUpdate(article1, mockBindingResult, model, mockPrincipal));
    }

    @Test
    void showArticle__nonExistentArticle() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        article1.setMember(new Member());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/152/show")
                .requestAttr("article", article1);

        // when
        when(articleService.findById(any())).thenReturn(null);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }

    @Test
    void showArticle() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        article1.setMember(new Member());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/1/show")
                .requestAttr("article", article1);

        // when
        when(articleService.findById(any())).thenReturn(article1);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("editId", -1L))
                .andExpect(model().attributeExists("commentToUpdate"))
                .andExpect(view().name("articles/show"));
    }

    @Test
    void showArticle__withEditId() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        Comment comment = new Comment();
        article1.setMember(new Member());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/1/show")
                .flashAttr("editId", 1L)
                .requestAttr("article", article1);

        // when
        when(articleService.findById(any())).thenReturn(article1);
        when(commentService.findById(any())).thenReturn(comment);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attribute("editId", 1L))
                .andExpect(model().attribute("commentToUpdate", comment))
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

    @Test
    void updateArticle_unAuthorizedUser() throws Exception {
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
        when(mockPrincipal.getName()).thenReturn("USER2"); // unauthorized user

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/1/show"));
    }

    @Test
    void deleteArticle_existingArticle() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Member member = new Member();
        member.setUsername("USER1");
        article1.setMember(member);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/1/delete")
                .principal(mockPrincipal)
                .requestAttr("article", article1);

        // when
        when(articleService.findById(any())).thenReturn(article1);
        when(mockPrincipal.getName()).thenReturn("USER1"); // authorized user

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles"));
        verify(articleService, times(1)).secureDelete(any(Article.class), anyString(), any(Principal.class));
    }

    @Test
    void deleteArticle_unavailableArticle() throws Exception {
        // given
        Article article1 = new Article();
        article1.setId(1L);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/articles/2/delete")
                .principal(mockPrincipal)
                .requestAttr("article", article1);

        // when
        when(articleService.findById(any())).thenReturn(null);
        when(mockPrincipal.getName()).thenReturn("USER1"); // authorized user

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles"));
        verify(articleService, times(0)).secureDelete(any(Article.class), anyString(), any(Principal.class));
    }

    @Test
    public void testGetRecipeNumberFormatException() throws Exception {
        mockMvc.perform(get("/articles/random_string/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/400"));
    }

}