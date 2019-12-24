package com.financescript.springapp.domains;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime creationTime;

    @PrePersist
    public void setCreationDateTime() {
        this.creationTime = LocalDateTime.now();
    }

    public boolean isNew() {
        return this.id == null;
    }

    public BaseEntity(Long id) {
        this.id = id;
    }
}
