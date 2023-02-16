package br.com.reinan.dscatalog.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.reinan.dscatalog.config.SecurityConfig;
import br.com.reinan.dscatalog.dto.CategoryDTO;
import br.com.reinan.dscatalog.services.CategoryService;
import br.com.reinan.dscatalog.tests.Factory;

@WebMvcTest(CategoryController.class)
@Import(SecurityConfig.class)
public class CategotyControllerTests {


    @Autowired
    private MockMvc mvc;
    @MockBean
    private CategoryService service;

    private PageImpl<CategoryDTO> page;
    private CategoryDTO dto;

    @BeforeEach
    public void setUp() {
        dto = Factory.createCategoryDto();
        page = new PageImpl<>(List.of(dto));
        when(service.findAll(any())).thenReturn(page);

    }

    @Test
    public void testFindAll() throws Exception {

        mvc.perform(get("/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).findAll(any(Pageable.class));
    }

}
