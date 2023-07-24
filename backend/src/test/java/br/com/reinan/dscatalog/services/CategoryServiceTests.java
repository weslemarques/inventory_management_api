package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.request.CategoryInsertDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import br.com.reinan.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRepository repository;

    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private CategoryServiceImpl service;

    private Long existsId;
    private Long notExistsId;
    private CategoryDTO dto;




    @BeforeEach
    public void setUp() {

        dto = Factory.createCategoryDto();
        existsId = 1L;
        Category category = Factory.createCategory();
        notExistsId = 1000L;
        PageImpl<Category> page = new PageImpl<>(List.of(category));

        Mockito.when(repository.findById(existsId)).thenReturn(Optional.of(category));
        Mockito.when(repository.findById(notExistsId)).thenReturn(Optional.empty());
        Mockito.when(repository.save(any())).thenReturn(category);
        Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);
        doNothing().when(repository).deleteById(existsId);
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(notExistsId);
    }


    @Test
    public void deleteShouldThrowsEmptyResultDataAccessException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(notExistsId));

        verify(repository).deleteById(notExistsId);
    }

    @Test
    public void findByIdShouldReturnOptionalNotEmptyWhenIdExists() {
        CategoryDTO responseDTO = service.findById(existsId);
        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals("category", dto.getName());

        verify(repository).findById(existsId);
    }

    @Test
    public void findByIdShouldReturnOptionalEmptyWhenIdExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(notExistsId));

        verify(repository).findById(notExistsId);
    }


    @Test
    public void insertShouldPersitObjectInDataBase() {
        Assertions.assertDoesNotThrow(() -> {
            service.insert(new CategoryInsertDTO());
        });

        verify(repository).save(any());
    }

    @Test
    public void updateShouldReturnEntityUpadate() {
        Assertions.assertDoesNotThrow(() -> {
            service.update(existsId, dto);
        });

        verify(repository).findById(existsId);
        verify(repository).save(any());
    }

    @Test
    public void updateShouldThrowsResorceNotFoundExceptionWhenNotExistsId() {
        Assertions.assertThrows(RuntimeException.class, () -> service.update(notExistsId, dto));

        verify(repository).findById(notExistsId);

    }

    @Test
    public void findAllShouldReturnPage() {

        Page<CategoryDTO> pageImpl = service.findAll(PageRequest.of(0, 10));

        Assertions.assertNotNull(pageImpl);
            Assertions.assertEquals(pageImpl.getNumber (), 0);
        Assertions.assertEquals(pageImpl.getSize(), 1);
        Assertions.assertEquals(pageImpl.getContent().get(0).getName(), "category");
    }
    @Test
    public void deleteShouldDoNothing() {
        Assertions.assertDoesNotThrow(() -> service.delete(existsId));
        verify(repository).deleteById(existsId);
    }

}
