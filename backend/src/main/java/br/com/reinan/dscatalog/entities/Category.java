package br.com.reinan.dscatalog.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_category")
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt = Instant.now();

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Nullable
    private Instant updatedAt;

    @ManyToMany(mappedBy = "categories")
    @Setter(AccessLevel.NONE)
    private final Set<Product> products = new HashSet<>();

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }


    public void setName(String name) {
        if (name != null) this.name = name;
    }

    public void setCreatedAt(Instant createdAt) {
        if (createdAt != null) this.createdAt = createdAt;
    }

    public void setUpdatedAt(@Nullable Instant updatedAt) {
        if (updatedAt != null)  this.updatedAt = updatedAt;
    }
}
