package com.financescript.springapp.dto;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.domains.SubComment;
import com.financescript.springapp.validation.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This DTO class is primarily created in order to prevent an exception when using a custom validation annotation
 * as an @Entity class.
 */

@Getter
@NoArgsConstructor
public class MemberDto {
    //TODO: Add validations
    private Long id;

    private LocalDateTime created;

    @NotNull(message = "is required")
    @ValidEmail
    private String email;

    private String username;

    @NotNull(message = "is required")
    @Size(min = 6, message = "Minimum of 6 characters is required")
    private String password;

    private String matchingPassword;

    private Set<Comment> comments = new HashSet<>();

    private Set<SubComment> subComments = new HashSet<>();

    private List<Article> articles = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * This setter attempts to take account of the fact that Email's domain is character insensitive.
     * https://www.quora.com/Are-email-addresses-case-sensitive
     */
    public void setEmail(String email) {
        String[] adjustedEmail = email.split("@");
        this.email = adjustedEmail[0].toLowerCase() + "@" + adjustedEmail[1];
    }

    public void setUsername(String username) {
        this.username = username.toUpperCase();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setSubComments(Set<SubComment> subComments) {
        this.subComments = subComments;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
