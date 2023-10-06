package br.com.reinan.dscatalog.repositories;

import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.factory.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long countTotalProducts;
    private long notExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        notExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenDoesNotExistsId() {
        Assertions.assertDoesNotThrow( () -> repository.deleteById(notExistingId));
    }

    @Test
    public void saveShouldPersistProductWhenIdIsNullAndAutoincrement() {
        Product product = ProductFactory.createProduct();
        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void findByIdShouldReturnOptionalNotEmptyWhenIdExists() {
        Optional<Product> obj = repository.findById(existingId);

        Assertions.assertTrue(obj.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalEmptyWhenIdExists() {
        Optional<Product> obj = repository.findById(notExistingId);

        Assertions.assertTrue(obj.isEmpty());
    }

    @Test
    public void findAllShouldReturnPage() {
        Page<Product> result = repository.findAll(PageRequest.of(1, 10));

        Assertions.assertNotNull(result);
    }

}
