package br.com.reinan.dscatalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.reinan.dscatalog.dto.ProductDto;
import br.com.reinan.dscatalog.services.ProductService;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import br.com.reinan.dscatalog.tests.Factory;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    private ProductDto productDto;
    private PageImpl<ProductDto> page;
    private Long existId;
    private Long notExistId;
    private Long dependenceId;

    @BeforeEach
    void setUpI() throws Exception {

        existId = 1L;
        notExistId = 2L;
        dependenceId = 3L;

        productDto = Factory.createProductDto();
        page = new PageImpl<>(List.of(productDto));

        when(service.findAll(any())).thenReturn(page);
        when(service.findById(existId)).thenReturn(productDto);
        doThrow(ResorceNotFoundException.class).when(service).findById(notExistId);
        doNothing().when(service).delete(existId);
        doThrow(ResorceNotFoundException.class).when(service).delete(notExistId);
        doThrow(DataBaseException.class).when(service).delete(dependenceId);

    }

    @Test
    public void testFindAll() throws Exception {

        mvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).findAll(any(Pageable.class));
    }

    @Test
    public void testFindById() throws Exception {
        mvc.perform(get("/products/{id}", existId))
                .andExpect(status().isOk());

        verify(service).findById(existId);
    }

    @Test
    public void testFindByIdThrowsResorceNotFoundException() throws Exception {
        mvc.perform(get("/products/{id}", notExistId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteVoid() throws Exception {
        mvc.perform(delete("/products/{id}", existId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteThrowsResorceNotFoundException() throws Exception {
        mvc.perform(delete("/products/{id}", notExistId))
                .andExpect(status().isNotFound());
    }

}
