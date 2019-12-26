package com.financescript.springapp.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SubComment extends BaseEntity {
    @ManyToOne
    private Comment comment;

    @ManyToOne
    private User user;

    @Lob
    private String contents;
}
