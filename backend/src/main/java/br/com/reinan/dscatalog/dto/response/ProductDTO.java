package br.com.reinan.dscatalog.dto.response;

import br.com.reinan.dscatalog.dto.base.ProductBaseDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@EqualsAndHashCode(of = "id", callSuper = false)
public class ProductDTO extends ProductBaseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Instant createAt;
    private Instant updateAt;

    //

    // name, price, date, description, img_url
    public ProductDTO(Product prod) {
        setId(prod.getId());
        setName(prod.getName());
        setDescription(prod.getDescription());
        setPrice(prod.getPrice());
        setImgUrl(prod.getImgUrl());
        setDate(prod.getDate());
        setCreateAt(prod.getCreatedAt());
        setUpdateAt(prod.getUpdatedAt());
    }

    public ProductDTO(Product prod, Set<Category> categories) {
        this(prod);
        categories.forEach(cat -> this.getCategories().add(new CategoryDTO(cat)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }



}
