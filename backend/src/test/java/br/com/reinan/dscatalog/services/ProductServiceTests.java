package br.com.reinan.dscatalog.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.reinan.dscatalog.dto.ProductDto;
import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import br.com.reinan.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService service;

    private Long existingId;
    private Long notExistingId;
    private Product product;
    private PageImpl<Product> page;

    @SuppressWarnings("deprecation")
    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        notExistingId = 1000L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));

        Mockito.when(repository.getOne(existingId)).thenReturn(product);
        Mockito.when(repository.getOne(notExistingId)).thenThrow(ResorceNotFoundException.class);
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(notExistingId)).thenReturn(Optional.empty());
        Mockito.when(repository.save(any())).thenReturn(product);
        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(ResorceNotFoundException.class).when(repository).deleteById(notExistingId);
        Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdexist() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {

        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.delete(notExistingId);
        });

        Mockito.verify(repository, times(1)).deleteById(notExistingId);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void updateShouldReturnEntityUpadate() {
        ProductDto entity = service.update(existingId, Factory.createProductDto());

        Assertions.assertNotNull(entity);
        Mockito.verify(repository, times(1)).getOne(existingId);
    }

    @Test
    public void updateShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.update(notExistingId, new ProductDto());
        });
    }

    @Test
    public void findByIdShouldReturnObjectNotNullWhenExistsId() {
        ProductDto obj = service.findById(existingId);

        Assertions.assertNotNull(obj);

        Mockito.verify(repository).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.findById(notExistingId);
        });

        Mockito.verify(repository).findById(notExistingId);
    }
}
