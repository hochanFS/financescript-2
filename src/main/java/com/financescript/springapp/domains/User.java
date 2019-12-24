package com.financescript.springapp.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Set<Comment> comments = new HashSet<>();

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Set<SubComment> subComments = new HashSet<>();

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Article> articles = new ArrayList<>();

    @Builder
    public User(Long id, String email, String username, String password, Set<Comment> comments,
                Set<SubComment> subComments, List<Article> articles) {
        super(id);
        this.email = email;
        this.username = username;
        this.password = password;
        if (comments != null)
            this.comments = comments;
        if (subComments != null)
            this.subComments = subComments;
        if (articles != null)
            this.articles = articles;
    }
}
