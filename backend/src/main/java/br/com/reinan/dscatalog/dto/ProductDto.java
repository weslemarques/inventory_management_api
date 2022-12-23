package br.com.reinan.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;

public class ProductDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Double price;

    private String imgUrl;

    private Instant date;

    private List<CategoryDto> categories = new ArrayList<>();

    public ProductDto() {

    }

    public ProductDto(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    // name, price, date, description, img_url
    public ProductDto(Product prod) {
        this.id = prod.getId();
        this.name = prod.getName();
        this.description = prod.getDescription();
        this.price = prod.getPrice();
        this.imgUrl = prod.getImgUrl();
        this.date = prod.getDate();
    }

    public ProductDto(Product prod, Set<Category> categories) {
        this(prod);
        categories.forEach(cat -> this.categories.add(new CategoryDto(cat)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
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
        ProductDto other = (ProductDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

}
