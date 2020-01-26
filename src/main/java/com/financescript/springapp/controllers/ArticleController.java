package com.financescript.springapp.controllers;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.domains.util.LocalDateTimeWriter;
import com.financescript.springapp.exceptions.ResourceNotFoundException;
import com.financescript.springapp.services.ArticleService;
import com.financescript.springapp.services.CommentService;
import com.financescript.springapp.services.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ArticleController {
    private static final String ARTICLE_LIST_URL = "articles/article-list";
    private static final String ARTICLE_FORM_URL = "articles/form";
    private final ArticleService articleService;
    private final MemberService memberService;
    private final CommentService commentService;
    private LocalDateTimeWriter localDateTimeWriter;

    @Autowired
    public ArticleController(ArticleService articleService, MemberService memberService, LocalDateTimeWriter localDateTimeWriter, CommentService commentService) {
        this.articleService = articleService;
        this.memberService = memberService;
        this.localDateTimeWriter = localDateTimeWriter;
        this.commentService = commentService;
    }

    @GetMapping("/articles")
    public String showArticleList(Article article, Model model) {
        if (article == null || article.getTitle() == null) {
            model.addAttribute("articles", articleService.findAllByOrderByCreationTimeDesc());
            model.addAttribute("converter", localDateTimeWriter);
            return ARTICLE_LIST_URL;
        }

        List<Article> articles = articleService.findAllByTitleLike(article.getTitle());
        model.addAttribute("articles", articles);
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
    public String showArticle(@PathVariable String id, Model model) {

        model.addAttribute("converter", localDateTimeWriter);
        model.addAttribute("article", articleService.findById(Long.valueOf(id)));
        model.addAttribute("comment", new Comment());
        String s;
        if (model.containsAttribute("editId")) {
            s = "" + model.getAttribute("editId");
            Comment comment = commentService.findById(Long.valueOf(s));
            model.addAttribute("commentToUpdate", comment);
        }
        else {
            model.addAttribute("editId", -1L);
            model.addAttribute("commentToUpdate", new Comment());
        }
        return "articles/show";
    }

    @GetMapping("/articles/{id}/update")
    public String updateArticle(@PathVariable String id, Model model, Principal principal) {
        Article article = articleService.findById(Long.valueOf(id));
        if (article == null) {
            return "redirect:/articles";
        }
        if (principal != null && principal.getName().equals(article.getMember().getUsername())) {
            model.addAttribute("article", article);
            return ARTICLE_FORM_URL;
        }
        return "redirect:/articles/" + id + "/show";
    }

    @GetMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable String id, Principal principal) {
        Article article = articleService.findById(Long.valueOf(id));
        if (article == null) {
            return "redirect:/articles";
        }
        articleService.secureDelete(article, article.getMember().getUsername(), principal);
        return "redirect:/articles";
    }


}
