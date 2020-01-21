package com.financescript.springapp.services.jpa;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.dto.tools.ArticleConverter;
import com.financescript.springapp.repositories.ArticleRepository;
import com.financescript.springapp.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;

@Service
public class ArticleJpaService implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;

    @Autowired
    public ArticleJpaService(ArticleRepository articleRepository, ArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
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
        article.getMember().addArticle(article);
        article.setContents(articleConverter.convert(article.getOriginalText()));
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    @PreAuthorize("#principal != null and (hasRole('ROLE_ADMIN') or #authorName.equals(#principal.name))")
    public void secureDelete(Article article, String authorName, Principal principal) {
        articleRepository.delete(article);
    }

    @Override
    public List<Article> findAllByTitleLike(String search) {
        return articleRepository.findAllByTitleLikeIgnoreCase("%" + search + "%");
    }
}
