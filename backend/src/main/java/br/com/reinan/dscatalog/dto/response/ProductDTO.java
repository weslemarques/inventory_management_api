package br.com.reinan.dscatalog.dto.response;

import br.com.reinan.dscatalog.dto.base.ProductBaseDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class ProductDTO extends ProductBaseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Instant createAt;
    private Instant updateAt;
    private Integer stock;

    public ProductDTO(Product prod) {
        setId(prod.getId());
        setName(prod.getName());
        setDescription(prod.getDescription());
        setPrice(prod.getPrice());
        setImgUrl(prod.getImgUrl());
        setDate(prod.getDate());
        setCreateAt(prod.getCreatedAt());
        setUpdateAt(prod.getUpdatedAt());
        setStock(prod.getStock());
    }

    public ProductDTO(Product prod, Set<Category> categories) {
        this(prod);
        categories.forEach(cat -> this.getCategories().add(new CategoryDTO(cat)));
    }


}
