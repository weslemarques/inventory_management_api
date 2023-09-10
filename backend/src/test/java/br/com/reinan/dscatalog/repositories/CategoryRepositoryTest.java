package br.com.reinan.dscatalog.repositories;

import java.util.Optional;

import br.com.reinan.dscatalog.util.factory.CategoryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.reinan.dscatalog.entities.Category;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    private Long existsId;
    private Long notExistsId;
    private Category category;

    @BeforeEach
    public void setUp() {
        existsId = 1L;
        notExistsId = 1000L;
        category = CategoryFactory.createInsertCategory();
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdexist() {
        repository.deleteById(existsId);

        Optional<Category> obj = repository.findById(existsId);

        Assertions.assertTrue(obj.isEmpty());
    }

    @Test
    public void deleteShouldThrowsEmptyResultDataAccessExceptionWhenNotExistsId() {
        Assertions.assertDoesNotThrow(() -> {
            repository.deleteById(notExistsId);
        });
    }

    @Test
    public void findByIdShouldReturnObjectNotNullWhenExistsId() {
        Optional<Category> obj = repository.findById(existsId);

        Assertions.assertTrue(obj.isPresent());
    }

    @Test
    public void findByIdShouldReturnObjectNullWhenExistsId() {
        Optional<Category> obj = repository.findById(notExistsId);
        Assertions.assertTrue(obj.isEmpty());
    }

    @Test
    public void saveShouldPersistProductWhenIdIsNullAndAutoincrement() {
        Category entity = repository.save(category);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(4L, entity.getId());
    }

    @Test
    public void findAllShouldReturnPage() {
        Page<Category> page = repository.findAll(PageRequest.of(0, 10));

        Assertions.assertNotNull(page);
    }
}
