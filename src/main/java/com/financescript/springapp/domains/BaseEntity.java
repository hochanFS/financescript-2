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
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime creationTime;

    @Column
    private LocalDateTime updateTime;

    @PrePersist
    public void setCreationDateTime() {
        this.creationTime = LocalDateTime.now();
    }

    @PreUpdate
    public void setChangeDateTime() {
        this.updateTime = LocalDateTime.now();
    }

    public boolean isNew() {
        return this.id == null;
    }

    public BaseEntity(Long id) {
        this.id = id;
    }
}
