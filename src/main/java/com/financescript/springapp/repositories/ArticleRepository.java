package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findAllByOrderByCreationTimeDesc();

    List<Article> findAllByTitleLike(String search);
}