package com.financescript.springapp.services;

import com.financescript.springapp.domains.Comment;

import java.security.Principal;

public interface CommentService {
    Comment findById(Long id);

    Comment save(Comment comment);

    void secureDelete(Comment comment, String authorName, Principal principal);

    void deleteById(Long id);
}
