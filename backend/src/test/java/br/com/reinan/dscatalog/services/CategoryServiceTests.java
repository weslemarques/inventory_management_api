package br.com.reinan.dscatalog.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.reinan.dscatalog.dto.CategoryDto;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import br.com.reinan.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    private Long existsId;
    private Long notExistsId;
    private Category category;
    private CategoryDto dto;

    @BeforeEach
    public void setUp() {
        dto = Factory.createCategoryDto();
        existsId = 1L;
        category = Factory.createCategory();
        notExistsId = 1000L;
        doNothing().when(repository).deleteById(existsId);
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(notExistsId);
        when(repository.findById(existsId)).thenReturn(Optional.of(category));
        when(repository.findById(notExistsId)).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(category);

    }

    @Test
    public void deleteShouldDoNothing() {
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existsId);
        });
        verify(repository).deleteById(existsId);
    }

    @Test
    public void deleteShouldThrowsEmptyResultDataAccessException() {
        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.delete(notExistsId);
        });

        verify(repository).deleteById(notExistsId);
    }

    @Test
    public void findByIdShouldReturnOptionalNotEmptyWhenIdExists() {
        CategoryDto dto = service.findById(existsId);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("category", dto.getName());

        verify(repository).findById(existsId);
    }

    @Test
    public void findByIdShouldReturnOptionalEmptyWhenIdExists() {
        Assertions.assertThrows(ResorceNotFoundException.class, () -> {
            service.findById(notExistsId);
        });

        verify(repository).findById(notExistsId);
    }

    @Test
    public void insertShouldPersitObjectInDataBase() {
        Assertions.assertDoesNotThrow(() -> {
            service.insert(dto);
        });

        verify(repository).save(any());
    }

    @Test
    public void updateShouldReturnEntityUpadate() {
        Assertions.assertDoesNotThrow(() -> {
            service.update(existsId, dto);
        });
    }

}
