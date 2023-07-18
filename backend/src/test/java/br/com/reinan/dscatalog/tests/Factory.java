package br.com.reinan.dscatalog.tests;

import java.time.Instant;

import br.com.reinan.dscatalog.dto.request.ProductRequestDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;

public class Factory {

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
        ProductDTO dto = new ProductDTO(prod, prod.getCategories());
        return dto;
    }

    public static Category createCategory() {
        Category category = new Category(1L, "category");
        return category;
    }

    public static Category createInsertCategory() {
        Category category = new Category("category");
        return category;
    }

    public static CategoryDTO createCategoryDto() {
        Category cate = createCategory();
        return new CategoryDTO(cate);
    }

}
