package com.financescript.springapp.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Article extends BaseEntity {
    @ManyToOne
    private Member member;

    @Size(min=6, max=60, message = "The title should be between 6 and 60 characters long")
    private String title;

    @Lob
    private String contents;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    private Set<Comment> comments = new HashSet<>();

    public Article addComment(Comment comment){
        comment.setArticle(this);
        this.comments.add(comment);
        return this;
    }

}
