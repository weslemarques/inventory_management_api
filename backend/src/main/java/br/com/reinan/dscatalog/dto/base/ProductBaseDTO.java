package br.com.reinan.dscatalog.dto.base;

import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    private Instant  createAt;
    private Instant  updateAt;

    public ProductBaseDTO() {

    }
    private List<CategoryDTO> categories = new ArrayList<>();

}
