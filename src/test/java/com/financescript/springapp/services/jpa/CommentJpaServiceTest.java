package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.repositories.CommentRepository;
import com.financescript.springapp.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CommentJpaServiceTest {

    @Mock
    CommentRepository commentRepository;

    CommentService commentService;

    private final Long COMMENT1_ID = 1L;
    private Comment comment1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List<Comment>comments = new ArrayList<>();
        commentService = new CommentJpaService(commentRepository);
        comment1 = new Comment();
        comment1.setId(COMMENT1_ID);
        comments.add(comment1);
        when(commentRepository.findAll()).thenReturn(comments);
    }

    @Test
    void findById() {
        commentService.findById(COMMENT1_ID);
        verify(commentRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        Comment comment2 = new Comment();
        comment2.setId(2L);
        commentService.save(comment2);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void delete() {
        // given
        Principal mockPrincipal = Mockito.mock(Principal.class);

        //when
        when(mockPrincipal.getName()).thenReturn("USER1");

        commentService.secureDelete(comment1, "USER1", mockPrincipal);
        verify(commentRepository, times(1)).delete(any(Comment.class));
    }

    @Test
    void deleteById() {
        commentService.deleteById(COMMENT1_ID);
        verify(commentRepository, times(1)).deleteById(anyLong());
    }
}