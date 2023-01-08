package br.com.reinan.dscatalog.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.reinan.dscatalog.dto.ProductDto;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServicesTests {

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    private Long existingId;
    private Long notExistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        notExistingId = 1000L;

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(ResorceNotFoundException.class).when(repository).deleteById(notExistingId);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdexist() {
        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingId);
        });

    }

}
