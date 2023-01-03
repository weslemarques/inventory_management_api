package br.com.reinan.dscatalog.tests;

import java.time.Instant;

import br.com.reinan.dscatalog.dto.ProductDto;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;

public class Factory {

    public static Product createProduct() {
        Product product = new Product("PS5", 600.0, Instant.parse("2020-07-20T10:00:00Z"),
                "The new generation PS5 video game", "");

        product.getCategories().add(new Category(2L, "Eletronics"));
        return product;
    }

    public static ProductDto createProductDto() {
        Product prod = createProduct();
        return new ProductDto(prod, prod.getCategories());
    }

}
