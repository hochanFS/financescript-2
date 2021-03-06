package com.financescript.springapp.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class Member extends BaseEntity {

    @Column(unique = true)
    private String email;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="password")
    private String password;

    @Column
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, mappedBy = "member")
    private Set<Comment> comments = new HashSet<>();

    @Column
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, mappedBy = "member")
    private Set<SubComment> subComments = new HashSet<>();

    @Column
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public Member addArticle(Article article){
        article.setMember(this);
        this.articles.add(article);
        return this;
    }

    public Member addComment(Comment comment){
        comment.setMember(this);
        this.comments.add(comment);
        return this;
    }

    public Member addRole(Role role) {
        this.roles.add(role);
        return this;
    }
}
