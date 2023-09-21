package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.request.CategoryInsertDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.security.filter.FilterToken;
import br.com.reinan.dscatalog.security.jwt.JwtUtils;
import br.com.reinan.dscatalog.services.CategoryServiceImpl;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import br.com.reinan.dscatalog.util.factory.CategoryFactory;
import br.com.reinan.dscatalog.util.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class CategotyControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CategoryServiceImpl service;

    @MockBean
    FilterToken filterToken;
    private Long existId;
    private Long notExistId;
    private Long dependenceId;
    PageImpl<CategoryDTO> page;
    String token;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUpI() {

        existId = 1L;
        notExistId = 2L;
        dependenceId = 3L;
        categoryDTO = CategoryFactory.createCategoryDto();
        CategoryInsertDTO categoryInsertDTO = CategoryFactory.requestCategory();
        page = new PageImpl<>(List.of(categoryDTO));


        when(service.findById(existId)).thenReturn(categoryDTO);
        doThrow(ResourceNotFoundException.class).when(service).findById(notExistId);

        doNothing().when(service).delete(existId);
        doThrow(ResourceNotFoundException.class).when(service).delete(notExistId);
        doThrow(DataBaseException.class).when(service).delete(dependenceId);


    }

    @Test
    public void testFindAll() throws Exception {
        mvc.perform(get("/v1/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).findAll(any(Pageable.class));
    }

}
