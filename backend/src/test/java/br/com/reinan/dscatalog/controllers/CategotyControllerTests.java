package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.config.WebSecurityConfig;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.services.CategoryServiceImpl;
import br.com.reinan.dscatalog.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class) 
@Import(WebSecurityConfig.class)
public class CategotyControllerTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CategoryServiceImpl service;

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

        mvc.perform(get("/v1/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).findAll(any(Pageable.class));
    }

}
