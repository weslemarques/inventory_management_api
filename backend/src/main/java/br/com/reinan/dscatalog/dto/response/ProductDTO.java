package br.com.reinan.dscatalog.dto.response;

import br.com.reinan.dscatalog.dto.base.ProductBaseDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDTO extends ProductBaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    public ProductDTO() {

    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        setId(id);
        setName(name);
        setDescription(description);
        setPrice(price);
        setImgUrl(imgUrl);
        setDate(date);
    }

    // name, price, date, description, img_url
    public ProductDTO(Product prod) {
        setId(prod.getId());
        setName(prod.getName());
        setDescription(prod.getDescription());
        setPrice(prod.getPrice());
        setImgUrl(prod.getImgUrl());
        setDate(prod.getDate());
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




    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductDTO other = (ProductDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
