package com.financescript.springapp.domains;

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
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private Set<Comment> comments = new HashSet<>();

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private Set<SubComment> subComments = new HashSet<>();

    @Column
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

}
