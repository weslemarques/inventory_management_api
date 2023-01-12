package br.com.reinan.dscatalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.reinan.dscatalog.dto.ProductDto;
import br.com.reinan.dscatalog.services.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @InjectMocks
    private ProductController controller;

    @Autowired
    private MockMvc mvc;

    @Mock
    private ProductService service;

    private Page<ProductDto> page;

    @Test
    public void testGet() throws Exception {
        // configurar o comportamento do myService.get()
        when(service.findAll(any())).thenReturn(page);
 
        // executar a requisição GET
        mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(page.toString()));



    }
}
