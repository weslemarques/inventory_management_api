package br.com.reinan.dscatalog.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_product")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Double price;
    private String imgUrl;

    private Integer stock;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt = Instant.now();

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant date;

    @JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ManyToMany(fetch = FetchType.EAGER)

    @Setter(AccessLevel.NONE)
    private Set<Category> categories = new HashSet<>();

    public Product(String name, String description, Double price, String imgUrl, int stock, Instant createdAt, Instant updatedAt, Instant date) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.date = date;
    }

    public Product(String name, double price, Instant date, String description, String imgUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }
    public void addCategory(Category category){
        categories.add(category);
    }



    public void setId(Long id) {
        if (id != null)  this.id = id;
    }
    public void setName(String name) {
        if (name != null)  this.name = name;
    }

    public void setDescription(String description) {
        if (description != null)  this.description = description;
    }

    public void setPrice(Double price) {
        if (price != null) this.price = price;
    }

    public void setImgUrl(String imgUrl) {
        if (imgUrl != null) this.imgUrl = imgUrl;
    }

    public void setStock(Integer stock) {
        if (stock != null) this.stock = stock;
    }

    public void setCreatedAt(Instant createdAt) {
        if (createdAt != null) this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        if (updatedAt != null) this.updatedAt = updatedAt;
    }

    public void setDate(Instant date) {
        if (date != null) this.date = date;
    }


}
