package br.com.reinan.dscatalog.integracao;

import br.com.reinan.dscatalog.dto.request.ProductRequestDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.services.ProductServiceImpl;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import br.com.reinan.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest()
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductServiceImpl service;
    private Long existingId;
    private Long notExistingId;

    private ProductRequestDTO requestDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        notExistingId = 1000L;
        ProductDTO dto = Factory.createProductDto();
        requestDTO = Factory.createProductRequest();
    }


    @Test
    public void deleteShouldThrowsResourceNotFoundExceptionWhenNotExistsId() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(notExistingId);
        });

    }

    @Test
    public void updateShouldReturnEntityUpadate() {
        ProductDTO entity = service.update(existingId, requestDTO);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("Percy Jackson", entity.getName());
        Assertions.assertEquals(40.0, entity.getPrice());

    }
    @Test
    public void deleteShouldDeleteObjectWhenIdexist() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
    }

    @Test
    public void updateShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(notExistingId, requestDTO);
        });
    }

    @Test
    public void findByIdShouldReturnObjectNotNullWhenExistsId() {
        ProductDTO obj = service.findById(existingId);

        Assertions.assertNotNull(obj);
        Assertions.assertEquals("The Lord of the Rings", obj.getName());

    }

    @Test
    public void findByIdShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(notExistingId);
        });

    }

    @Test
    public void findAllShouldReturnPage() {
        Pageable page = PageRequest.of(1, 10);

        Page<ProductDTO> result = service.findAll(page);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(1, result.getNumber());

    }

    @Test
    public void insertShouldPersistEntityInDataBase() {
        Assertions.assertDoesNotThrow(() -> {
            service.insert(Factory.createProductRequest());
        });
    }
}
