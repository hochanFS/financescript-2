package com.financescript.springapp.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @ManyToOne
    private User author;

    @ManyToOne
    private Article article;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment")
    private List<SubComment> subComments = new ArrayList<>();

    @Lob
    private String contents;

    @Builder
    public Comment(Long id, User author, List<SubComment> subComments, String contents) {
        super(id);
        this.author = author;
        if (subComments != null)
            this.subComments = subComments;
        this.contents = contents;
    }
}
