package br.com.reinan.dscatalog.integracao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.com.reinan.dscatalog.dto.ProductDto;
import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.services.ProductService;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import br.com.reinan.dscatalog.tests.Factory;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductService service;
    private Long existingId;
    private Long notExistingId;
    private Product product;
    private ProductDto dto;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        notExistingId = 1000L;
        product = Factory.createProduct();
        dto = Factory.createProductDto();

    }

    @Test
    public void deleteShouldDeleteObjectWhenIdexist() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
    }

    @Test
    public void deleteShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {

        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.delete(notExistingId);
        });

    }

    @Test
    public void updateShouldReturnEntityUpadate() {
        ProductDto entity = service.update(existingId, new ProductDto(product));

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("PS5", entity.getName());
        Assertions.assertEquals(600.0, entity.getPrice());

    }

    @Test
    public void updateShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.update(notExistingId, dto);
        });
    }

    @Test
    public void findByIdShouldReturnObjectNotNullWhenExistsId() {
        ProductDto obj = service.findById(existingId);

        Assertions.assertNotNull(obj);
        Assertions.assertEquals("The Lord of the Rings", obj.getName());

    }

    @Test
    public void findByIdShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.findById(notExistingId);
        });

    }

    @Test
    public void findAllShouldReturnPage() {
        Pageable page = PageRequest.of(1, 10);

        Page<ProductDto> result = service.findAll(page);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(1, result.getNumber());

    }

    @Test
    public void insertShouldPersistEntityInDataBase() {
        Assertions.assertDoesNotThrow(() -> {
            service.insert(new ProductDto(product));
        });
    }
}
