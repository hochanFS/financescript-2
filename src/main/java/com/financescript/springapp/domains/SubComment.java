package com.financescript.springapp.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SubComment extends BaseEntity {
    @ManyToOne
    private Comment comment;

    @ManyToOne
    private User author;

    @Lob
    private String contents;

    @Builder
    public SubComment(Long id, User author, String contents) {
        super(id);
        this.author = author;
        this.contents = contents;
    }
}
