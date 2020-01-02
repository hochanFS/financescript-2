package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.util.LocalDateTimeWriter;
import com.financescript.springapp.services.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ArticleController {
    private static final String ARTICLE_LIST_URL = "articles/article-list";
    private final ArticleService articleService;
    private LocalDateTimeWriter localDateTimeWriter;

    public ArticleController(ArticleService articleService, LocalDateTimeWriter localDateTimeWriter) {
        this.articleService = articleService;
        this.localDateTimeWriter = localDateTimeWriter;
    }

    @GetMapping("/articles")
    public String showArticleList(Model model) {
        model.addAttribute("articles", articleService.findAllByOrderByCreationTimeDesc());
        model.addAttribute("converter", localDateTimeWriter);
        return ARTICLE_LIST_URL;
    }
}
