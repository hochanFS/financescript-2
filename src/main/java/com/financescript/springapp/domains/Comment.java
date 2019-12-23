package com.financescript.springapp.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Article article;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SubComment> subComments;

    @Lob
    private String contents;

    @Builder
    public Comment(Long id, User user, List<SubComment> subComments, String contents) {
        super(id);
        this.user = user;
        this.subComments = subComments;
        this.contents = contents;
    }
}
