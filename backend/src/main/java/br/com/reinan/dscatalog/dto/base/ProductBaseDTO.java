package br.com.reinan.dscatalog.dto.base;

import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public abstract class ProductBaseDTO {

    @Size(min = 5, max = 60)
    @NotBlank(message = "field required")
    private String name;
    @Positive(message = "the price must be positive")
    private Double price;
    @PastOrPresent(message = "A data do produto nao pode ser futura")
    private Instant date;
    private String description;

    private String imgUrl;

    public ProductBaseDTO() {

    }

    public ProductBaseDTO(String name, Double price, Instant date, String description, String imgUrl) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    private List<CategoryDTO> categories = new ArrayList<>();

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


    public List<CategoryDTO> getCategories() {
        return categories;
    }

}
