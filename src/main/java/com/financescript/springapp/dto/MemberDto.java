package com.financescript.springapp.dto;

import com.financescript.springapp.domains.Article;
import com.financescript.springapp.domains.Comment;
import com.financescript.springapp.domains.SubComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Setter
@NoArgsConstructor
public class MemberDto {
    //TODO: Add validations
    private Long id;

    private LocalDateTime created;

    private String email;

    private String username;

    private String password;

    private String matchingPassword;

    private Set<Comment> comments = new HashSet<>();

    private Set<SubComment> subComments = new HashSet<>();

    private List<Article> articles = new ArrayList<>();
}
