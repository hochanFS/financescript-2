package com.financescript.springapp.services;

import com.financescript.springapp.domains.Article;
import org.springframework.security.access.prepost.PreAuthorize;

import java.security.Principal;
import java.util.List;

public interface ArticleService {
    List<Article> findAllByOrderByCreationTimeDesc();

    Article findById(Long id);

    Article save(Article article);

    List<Article> findAllByTitleLike(String search);

    void secureDelete(Article article, String authorName, Principal principal);
}
