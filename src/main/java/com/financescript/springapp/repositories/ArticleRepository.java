package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAllByOrderByCreationTimeDesc();

    List<Article> findAllByTitleLikeIgnoreCase(String search);

    void delete(Article article);
}