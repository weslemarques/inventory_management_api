package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.request.ProductRequestDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import br.com.reinan.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
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

    private ProductRequestDTO requestDTO;
    @Mock

    private ModelMapper mapper;

    @BeforeEach
    void setUp() {

        existingId = 1L;
        notExistingId = 1000L;
        Product product = Factory.createProduct();
        Category category = Factory.createCategory();
        requestDTO = Factory.createProductRequest();
        PageImpl<Product> page = new PageImpl<>(List.of(product));

        when(repository.findById(existingId)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(existingId)).thenReturn(Optional.of(category));
        when(repository.findById(notExistingId)).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(product);
        doNothing().when(repository).deleteById(existingId);
        doThrow(ResourceNotFoundException.class).when(repository).deleteById(notExistingId);
        when(repository.findAll((Pageable) any())).thenReturn(page);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdexist() {
        Assertions.assertDoesNotThrow(() -> service.delete(existingId));

        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteShouldThrowsResourceNotFoundExceptionWhenNotExistsId() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(notExistingId));

        verify(repository, times(1)).deleteById(notExistingId);
    }

    @Test
    public void updateShouldReturnEntityUpdate() {
        ProductDTO entity = service.update(existingId, requestDTO);

        Assertions.assertNotNull(entity);
        verify(repository).findById(existingId);
    }

    @Test
    public void updateShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(notExistingId, requestDTO));

        verify(repository).findById(notExistingId);
    }

    @Test
    public void findByIdShouldReturnObjectNotNullWhenExistsId() {
        ProductDTO obj = service.findById(existingId);

        Assertions.assertNotNull(obj);

        verify(repository).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(notExistingId));

        verify(repository).findById(notExistingId);
    }

    @Test
    public void findAllShouldReturnPage() {
        Pageable pageable = PageRequest.of(1, 10);

        Page<ProductDTO> result = service.findAll(pageable);

        Assertions.assertNotNull(result);

        verify(repository).findAll(pageable);
    }

    @Test
    public void insertShouldPersistEntityInDataBase() {
        Assertions.assertDoesNotThrow(() -> {
            service.insert(requestDTO);
        });
    }

}
