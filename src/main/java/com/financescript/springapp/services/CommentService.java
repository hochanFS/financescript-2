package com.financescript.springapp.services;

import com.financescript.springapp.domains.Comment;

public interface CommentService {
    Comment save(Comment comment);

    void delete(Comment comment);

    void deleteById(Long id);
}
