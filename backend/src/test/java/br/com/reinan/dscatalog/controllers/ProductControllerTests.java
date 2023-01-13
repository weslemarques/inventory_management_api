package br.com.reinan.dscatalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import br.com.reinan.dscatalog.dto.ProductDto;
import br.com.reinan.dscatalog.services.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    @Test
    public void testFindAll() throws Exception {
        // Configure o comportamento do objeto mockado
        Page<ProductDto> products = new PageImpl<>(List.of());
        when(service.findAll(any(Pageable.class))).thenReturn(products);

        // Execute o método que você deseja testar
        MockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(products)));

        // Verifique se o método foi chamado corretamente
        verify(service).findAll(any(Pageable.class));
    }

}
