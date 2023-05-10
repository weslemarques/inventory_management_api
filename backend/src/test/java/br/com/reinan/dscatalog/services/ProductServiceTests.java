package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.ProductDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import br.com.reinan.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl service;

    private Long existingId;
    private Long notExistingId;
    private Product product;
    private PageImpl<Product> page;
    private Category category;
    private ProductDTO dto;

    @BeforeEach
    void setUp() throws Exception {

        existingId = 1L;
        notExistingId = 1000L;
        product = Factory.createProduct();
        category = Factory.createCategory();
        dto = Factory.createProductDto();
        page = new PageImpl<>(List.of(product));

        when(repository.findById(existingId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(existingId)).thenReturn(Optional.of(category));
        when(repository.findById(notExistingId)).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(product);
        doNothing().when(repository).deleteById(existingId);
        doThrow(ResorceNotFoundException.class).when(repository).deleteById(notExistingId);
        Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdexist() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {

        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.delete(notExistingId);
        });

        verify(repository, times(1)).deleteById(notExistingId);
    }

    @Test
    public void updateShouldReturnEntityUpadate() {
        ProductDTO entity = service.update(existingId, dto);

        Assertions.assertNotNull(entity);
        verify(repository).findById(existingId);
    }

    @Test
    public void updateShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.update(notExistingId, new ProductDTO());
        });

        verify(repository).findById(notExistingId);
    }

    @Test
    public void findByIdShouldReturnObjectNotNullWhenExistsId() {
        ProductDTO obj = service.findById(existingId);

        Assertions.assertNotNull(obj);

        verify(repository).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.findById(notExistingId);
        });

        verify(repository).findById(notExistingId);
    }

    @Test
    public void findAllShouldReturnPage() {
        Pageable page = PageRequest.of(1, 10);

        Page<ProductDTO> result = service.findAll(page);

        Assertions.assertNotNull(result);

        verify(repository).findAll(page);
    }

    @Test
    public void insertShouldPersistEntityInDataBase() {
        Assertions.assertDoesNotThrow(() -> {
            service.insert(new ProductDTO(product));
        });
    }

}
