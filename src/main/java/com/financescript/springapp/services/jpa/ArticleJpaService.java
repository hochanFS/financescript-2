package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.User;
import com.financescript.springapp.services.ArticleService;

import java.util.List;

public class ArticleJpaService implements ArticleService {
    @Override
    public List<Article> findRecent10() {
        return null;
    }

    @Override
    public Article findById() {
        return null;
    }

    @Override
    public Article save(Article article) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Article article) {

    }

    @Override
    public Article findByUser(User user) {
        return null;
    }
}
