package br.com.reinan.dscatalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.reinan.dscatalog.dto.ProductDto;
import br.com.reinan.dscatalog.services.ProductService;
import br.com.reinan.dscatalog.tests.Factory;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    private ProductDto productDto;
    private PageImpl<ProductDto> page;

    @BeforeEach
    void setUpI() throws Exception {

        productDto = Factory.createProductDto();
        page = new PageImpl<>(List.of(productDto));

        when(service.findAll(any())).thenReturn(page);
    }

    @Test
    public void testFindAll() throws Exception {

        mvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).findAll(any(Pageable.class));
    }

}
