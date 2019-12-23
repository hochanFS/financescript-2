package com.financescript.springapp.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    private Set<SubComment> subComments;

    @Column
    @OneToMany
    private Set<Article> articles;

    @Builder
    public User(Long id, String email, String username, String password, Set<Comment> comments,
                Set<SubComment> subComments, Set<Article> articles) {
        super(id);
        this.email = email;
        this.username = username;
        this.password = password;
        this.comments = comments;
        this.subComments = subComments;
        this.articles = articles;
    }
}
