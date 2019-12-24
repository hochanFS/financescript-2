package com.financescript.springapp.services;

import com.financescript.springapp.domains.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAllByOrderByCreationTimeDesc();

    Article findById(Long id);

    Article save(Article article);

    void deleteById(Long id);

    void delete(Article article);

    List<Article> findAllByTitleLike(String search);
}
