package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.util.LocalDateTimeWriter;
import com.financescript.springapp.services.ArticleService;
import com.financescript.springapp.services.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
public class ArticleController {
    private static final String ARTICLE_LIST_URL = "articles/article-list";
    private static final String ARTICLE_FORM_URL = "articles/form";
    private final ArticleService articleService;
    private final MemberService memberService;
    private LocalDateTimeWriter localDateTimeWriter;

    public ArticleController(ArticleService articleService, MemberService memberService,LocalDateTimeWriter localDateTimeWriter) {
        this.articleService = articleService;
        this.memberService = memberService;
        this.localDateTimeWriter = localDateTimeWriter;
    }

    @GetMapping("/articles")
    public String showArticleList(Model model) {
        model.addAttribute("articles", articleService.findAllByOrderByCreationTimeDesc());
        model.addAttribute("converter", localDateTimeWriter);
        return ARTICLE_LIST_URL;
    }

    @GetMapping("/articles/new")
    public String newRecipe(Model model, Principal principal){
        if (principal == null) {
            model.addAttribute("alertMessage", "The user is required to log in before using");
            return "redirect:/login";
        }
        model.addAttribute("article", new Article());

        return "articles/form";
    }

    @PostMapping("/articles/new")
    public String saveOrUpdate(@Valid @ModelAttribute("article") Article article, Model model, BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return ARTICLE_FORM_URL;
        }
        if (principal == null) {
            model.addAttribute("alertMessage", "Unknown user"); // TODO: consider throwing 403 error
            return "security/login";
        }
        article.setMember(memberService.findByUsername(principal.getName()));
        Article newArticle = articleService.save(article);
        return "redirect:/articles/" + newArticle.getId() + "/show";
    }

    @GetMapping("/articles/{id}/show")
    public String showArticle(@PathVariable String id, Model model, Principal principal) {
        model.addAttribute("converter", localDateTimeWriter);
        model.addAttribute("article", articleService.findById(Long.valueOf(id)));
        if (principal != null)
            model.addAttribute("sessionUser", principal.getName());
        return "articles/show"; // TODO: create a show template
    }
}
