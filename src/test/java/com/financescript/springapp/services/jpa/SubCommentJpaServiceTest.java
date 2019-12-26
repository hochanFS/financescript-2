package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.SubComment;
import com.financescript.springapp.repositories.SubCommentRepository;
import com.financescript.springapp.services.SubCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SubCommentJpaServiceTest {

    @Mock
    SubCommentRepository subCommentRepository;

    SubCommentService subCommentService;

    private final Long SUB_COMMENT1_ID = 1L;
    private SubComment subComment1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List<SubComment> subComments = new ArrayList<>();
        subCommentService = new SubCommentJpaService(subCommentRepository);
        subComment1 = new SubComment();
        subComment1.setId(SUB_COMMENT1_ID);
        subComments.add(subComment1);
        when(subCommentRepository.findAll()).thenReturn(subComments);
    }

    @Test
    void save() {
        SubComment subComment2 = new SubComment();
        subComment2.setId(2L);
        subCommentService.save(subComment2);
        verify(subCommentRepository, times(1)).save(any(SubComment.class));
    }

    @Test
    void delete() {
        subCommentService.delete(subComment1);
        verify(subCommentRepository, times(1)).delete(any(SubComment.class));
    }

    @Test
    void deleteById() {
        subCommentService.deleteById(SUB_COMMENT1_ID);
        verify(subCommentRepository, times(1)).deleteById(anyLong());
    }
}