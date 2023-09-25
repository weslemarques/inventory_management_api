package br.com.reinan.dscatalog.integracao;

import br.com.reinan.dscatalog.dto.request.ProductRequestDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.services.CategoryServiceImpl;
import br.com.reinan.dscatalog.services.ProductServiceImpl;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import br.com.reinan.dscatalog.util.factory.CategoryFactory;
import br.com.reinan.dscatalog.util.factory.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest()
@Transactional
@ActiveProfiles("test")
public class CategoryServiceIT {

    @Autowired
    private CategoryServiceImpl service;
    private Long existingId;
    private Long notExistingId;

    private CategoryDTO requestDTO;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        notExistingId = 1000L;
        requestDTO = CategoryFactory.createCategoryDto();
    }


    @Test
    public void deleteShouldThrowsResourceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertDoesNotThrow( () -> service.delete(notExistingId));
    }

    @Test
    public void updateShouldReturnEntityUpadate() {
        CategoryDTO entity = service.update(existingId, requestDTO);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(1L, entity.getId());
        Assertions.assertEquals("category", entity.getName());

    }
    @Test
    public void deleteShouldDeleteObjectWhenIdexist() {
        Assertions.assertDoesNotThrow(() -> service.delete(existingId));
    }

    @Test
    public void updateShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(notExistingId, requestDTO));
    }

    @Test
    public void findByIdShouldReturnObjectNotNullWhenExistsId() {
        CategoryDTO obj = service.findById(existingId);

        Assertions.assertNotNull(obj);
        Assertions.assertEquals("Books", obj.getName());

    }

    @Test
    public void findByIdShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(notExistingId));

    }

    @Test
    public void findAllShouldReturnPage() {
        Pageable page = PageRequest.of(1, 10);

        Page<CategoryDTO> result = service.findAll(page);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(1, result.getNumber());

    }

    @Test
    public void insertShouldPersistEntityInDataBase() {
        Assertions.assertDoesNotThrow(() -> {
            service.insert(CategoryFactory.requestCategory());
        });
    }
}
