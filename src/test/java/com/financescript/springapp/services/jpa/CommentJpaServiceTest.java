package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.repositories.CommentRepository;
import com.financescript.springapp.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        comment1 = Comment.builder().id(COMMENT1_ID).build();
        comments.add(comment1);
        when(commentRepository.findAll()).thenReturn(comments);
    }

    @Test
    void save() {
        commentService.save(Comment.builder().id(2L).build());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void delete() {
        commentService.delete(comment1);
        verify(commentRepository, times(1)).delete(any(Comment.class));
    }

    @Test
    void deleteById() {
        commentService.deleteById(COMMENT1_ID);
        verify(commentRepository, times(1)).deleteById(anyLong());
    }
}