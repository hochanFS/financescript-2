package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.domains.Member;
import com.financescript.springapp.domains.util.LocalDateTimeWriter;
import com.financescript.springapp.services.ArticleService;
import com.financescript.springapp.services.CommentService;
import com.financescript.springapp.services.MemberService;
import com.google.inject.internal.MoreTypes;
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

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class CommentControllerTest {

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

    CommentController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new CommentController(articleService, memberService, commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    void writeCommentToArticle__invalidArticle() throws Exception {
        //given
        Article article1 = new Article();
        Comment comment = new Comment();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/articles/1/comment")
                .requestAttr("comment", comment);

        //when
        when(articleService.findById(anyLong())).thenReturn(null);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles"));
    }

    @Test
    void writeCommentToArticle__nullPrincipal() throws Exception {
        //given
        Article article1 = new Article();
        Comment comment = new Comment();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/articles/1/comment")
                .requestAttr("comment", comment);

        //when
        when(articleService.findById(anyLong())).thenReturn(article1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    void writeCommentToArticle__validRequest() throws Exception {
        //given
        Article article1 = new Article();
        article1.setId(1L);
        Comment comment = new Comment();
        Member member = new Member();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        comment.setContents("Some comments");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/articles/1/comment")
                .requestAttr("comment", comment)
                .principal(mockPrincipal);

        //when
        when(articleService.findById(anyLong())).thenReturn(article1);
        when(memberService.findByUsername(anyString())).thenReturn(member);
        when(commentService.save(any(Comment.class))).thenReturn(comment);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/1/show"));
    }


    @Test
    void deleteArticle_unavailableComment() throws Exception {
        // given
        Comment comment = new Comment();
        comment.setId(1L);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/comment/1/delete")
                .principal(mockPrincipal);

        // when
        when(commentService.findById(any())).thenReturn(null);
        when(mockPrincipal.getName()).thenReturn("USER1"); // authorized user

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles"));
        verify(commentService, times(0)).secureDelete(any(Comment.class), anyString(), any(Principal.class));
    }

    @Test
    void deleteArticle_validComment() throws Exception {
        // given
        Comment comment = new Comment();
        Article article = new Article();
        article.setId(2L);
        comment.setArticle(article);
        Member member = new Member();
        member.setUsername("USER1");
        comment.setMember(member);
        comment.setId(1L);
        Principal mockPrincipal = Mockito.mock(Principal.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/comment/1/delete")
                .principal(mockPrincipal);

        // when
        when(commentService.findById(any())).thenReturn(comment);
        when(mockPrincipal.getName()).thenReturn("USER1"); // authorized user

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/2/show"));
        verify(commentService, times(1)).secureDelete(any(Comment.class), anyString(), any(Principal.class));
    }

    @Test
    void deleteArticle_nullPrincipal() throws Exception {
        // given
        Comment comment = new Comment();
        Article article = new Article();
        article.setId(2L);
        comment.setArticle(article);
        Member member = new Member();
        member.setUsername("USER1");
        comment.setMember(member);
        comment.setId(1L);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/comment/1/delete");

        // when
        when(commentService.findById(any())).thenReturn(comment);

        // then
        mockMvc.perform(requestBuilder)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
        verify(commentService, times(0)).secureDelete(any(Comment.class), anyString(), any(Principal.class));
    }

}