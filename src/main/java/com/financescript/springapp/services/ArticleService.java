package com.financescript.springapp.services;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.User;

import java.util.List;

public interface ArticleService {
    List<Article> findRecent10();

    Article findById();

    Article save(Article article);

    void deleteById(Long id);

    void delete(Article article);

    Article findByUser(User user);
}
