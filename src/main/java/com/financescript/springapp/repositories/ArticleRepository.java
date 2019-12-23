package com.financescript.springapp.repositories;

import com.financescript.springapp.domains.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
}