package com.financescript.springapp.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article extends BaseEntity {
    @ManyToOne
    private Member member;

    private String title;

    @Lob
    private String contents;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private Set<Comment> comments = new HashSet<>();

    public Article addComment(Comment comment){
        comment.setArticle(this);
        this.comments.add(comment);
        comment.getMember().getComments().add(comment);
        return this;
    }

}
