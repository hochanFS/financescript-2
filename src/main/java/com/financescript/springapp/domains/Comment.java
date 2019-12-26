package com.financescript.springapp.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Article article;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment")
    private Set<SubComment> subComments = new HashSet<>();

    @Lob
    private String contents;

    public Comment addSubComment(SubComment subComment) {
        subComment.setComment(this);
        this.subComments.add(subComment);
        return this;
    }
}
