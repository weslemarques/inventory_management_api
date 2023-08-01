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
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;

    private Long existsId;
    private Long notExistsId;
    private CategoryDTO dto;

    List<Category> categories ;

    @Mock
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 806c5fce3fb9329dbdcf52bddf328bfa02b79d56
=======
>>>>>>> main
>>>>>>> 70defb18e289a4918cc4bdf8519f758b0032d35c
>>>>>>> bfd541ad380ccf5b59c6e14528da2be46dacb169

    private ModelMapper mapper;


    @BeforeEach
    public void setUp() {

        dto = Factory.createCategoryDto();
        existsId = 1L;
        categories = new ArrayList<>();
        Category category = Factory.createCategory();
        categories.add(category);
        notExistsId = 1000L;
        doNothing().when(repository).deleteById(existsId);

        when(repository.findById(existsId)).thenReturn(Optional.of(category));
        when(repository.findById(notExistsId)).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(category);
        when(repository.findAll(isA(Pageable.class))).thenReturn(new PageImpl<>(categories));
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 806c5fce3fb9329dbdcf52bddf328bfa02b79d56
=======
>>>>>>> main
>>>>>>> 70defb18e289a4918cc4bdf8519f758b0032d35c
>>>>>>> bfd541ad380ccf5b59c6e14528da2be46dacb169
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(notExistsId);
    }


    @Test
    public void deleteShouldThrowsEmptyResultDataAccessException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(notExistsId));

        verify(repository).deleteById(notExistsId);
    }

    @Test
    public void findByIdShouldReturnOptionalNotEmptyWhenIdExists() {
        var dto = service.findById(existsId);
        assertNotNull(dto);
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

        Page<CategoryDTO> pageImpl = service.findAll(PageRequest.of(0,2));
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 806c5fce3fb9329dbdcf52bddf328bfa02b79d56
=======
>>>>>>> main
>>>>>>> 70defb18e289a4918cc4bdf8519f758b0032d35c
>>>>>>> bfd541ad380ccf5b59c6e14528da2be46dacb169

        assertNotNull(pageImpl);
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
