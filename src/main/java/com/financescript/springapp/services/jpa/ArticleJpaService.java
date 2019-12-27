package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.repositories.ArticleRepository;
import com.financescript.springapp.services.ArticleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ArticleJpaService implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleJpaService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findAllByOrderByCreationTimeDesc() {
        return articleRepository.findAllByOrderByCreationTimeDesc();
    }

    @Override
    public Article findById(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null);
    }

    @Override
    @Transactional
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public List<Article> findAllByTitleLike(String search) {
        return articleRepository.findAllByTitleLike("%" + search + "%");
    }
}
