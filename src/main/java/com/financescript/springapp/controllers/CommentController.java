package com.financescript.springapp.controllers;


import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.exceptions.ResourceNotFoundException;
import com.financescript.springapp.services.ArticleService;
import com.financescript.springapp.services.CommentService;
import com.financescript.springapp.services.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
public class CommentController {
    private final ArticleService articleService;
    private final MemberService memberService;
    private final CommentService commentService;

    @Autowired
    public CommentController(ArticleService articleService, MemberService memberService, CommentService commentService) {
        this.articleService = articleService;
        this.memberService = memberService;
        this.commentService = commentService;
    }

    @PostMapping("/articles/{id}/comment")
    public String writeCommentsToArticle(@PathVariable String id, Comment comment, Model model, Principal principal) {
        Article article = articleService.findById(Long.valueOf(id));
        if (article == null) {
            return "redirect:/articles";
        }
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("comment", new Comment());
        comment.setArticle(article);
        comment.setMember(memberService.findByUsername(principal.getName()));
        article.addComment(comment);
        commentService.save(comment);
        return "redirect:/articles/" + id + "/show";
    }

    @GetMapping("comment/{id}/delete")
    public String deleteComment(@PathVariable String id, Principal principal) {
        Comment tempComment = commentService.findById(Long.valueOf(id));
        if (tempComment == null) {
            return "redirect:/articles";
        }
        if (principal == null) {
            return "redirect:/login";
        }
        Long articleId = tempComment.getArticle().getId();
        commentService.secureDelete(tempComment, tempComment.getMember().getUsername(), principal);
        return "redirect:/articles/" + articleId + "/show";
    }

    @GetMapping("/comment/{cid}/edit")
    public String expandComment(@PathVariable String cid, RedirectAttributes redirectAttributes, Principal principal) {
        Comment comment = commentService.findById(Long.valueOf(cid));
        if (comment == null) {
            throw new ResourceNotFoundException();
        }
        Article article = comment.getArticle();
        String id = article.getId().toString();

        if (principal != null && principal.getName().equals(comment.getMember().getUsername())) {
            redirectAttributes.addFlashAttribute("editId", Long.valueOf(cid));
        }
        return "redirect:/articles/" + id + "/show#form" + cid;
    }

    @PostMapping("/comment/{cid}/update")
    public String updateComment(@PathVariable String cid, @RequestParam("content") String content, Principal principal) {
        Comment comment = commentService.findById(Long.valueOf(cid));
        if (comment == null) {
            throw new ResourceNotFoundException();
        }
        if (principal == null) {
            return "redirect:/login";
        }
        Article article = comment.getArticle();
        String id = article.getId().toString();
        comment.setContents(content);
        commentService.save(comment);
        return "redirect:/articles/" + id + "/show";
    }
}
