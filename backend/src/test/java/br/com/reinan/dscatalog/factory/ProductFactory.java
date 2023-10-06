package br.com.reinan.dscatalog.factory;

import br.com.reinan.dscatalog.dto.request.ProductRequestDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;

import java.time.Instant;

public class ProductFactory {

    public static Product createProduct() {
        Product product = new Product("PS5 Plus", 600.0, Instant.parse("2020-07-20T10:00:00Z"),
                "The new generation PS5 video game", "");
        product.getCategories().add(new Category(1L, "Eletronics"));
        return product;
    }

    public static Product createProductUpdate() {
        Product product = new Product();

        product.getCategories().add(new Category(1L, "Books"));
        return product;
    }
    public static ProductRequestDTO createProductRequest() {
        ProductRequestDTO request = new ProductRequestDTO("Percy Jackson", 40.0, Instant.parse("2020-07-20T10:00:00Z"),
                "Percy Jackson Book", "");
        request.getCategories().add(new CategoryDTO(1L, "Eletronics"));
        return request;
    }

    public static ProductDTO createProductDto() {
        Product prod = createProduct();
        return new ProductDTO(prod, prod.getCategories());
    }
}
