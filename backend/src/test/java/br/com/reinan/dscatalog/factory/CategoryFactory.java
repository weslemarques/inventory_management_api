package br.com.reinan.dscatalog.factory;

import br.com.reinan.dscatalog.dto.request.CategoryInsertDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.entities.Category;

public class CategoryFactory {

    public static Category createCategory() {
        return new Category(1L, "category");

    }

    public static Category createInsertCategory() {

        return new Category("category");
    }

    public static CategoryDTO createCategoryDto() {
        Category cate = createCategory();
        return new CategoryDTO(cate);
    }

    public static CategoryInsertDTO requestCategory() {

        return new CategoryInsertDTO("category");
    }
}
