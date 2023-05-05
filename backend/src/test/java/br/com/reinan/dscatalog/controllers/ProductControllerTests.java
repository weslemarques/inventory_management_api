package br.com.reinan.dscatalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

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
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.reinan.dscatalog.config.WebSecurityConfig;
import br.com.reinan.dscatalog.dto.ProductDTO;
import br.com.reinan.dscatalog.services.ProductServiceImpl;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import br.com.reinan.dscatalog.tests.Factory;

@WebMvcTest(ProductController.class)
@Import(WebSecurityConfig.class)
public class ProductControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductServiceImpl service;

    private ProductDTO productDto;
    private PageImpl<ProductDTO> page;
    private Long existId;
    private Long notExistId;
    private Long dependenceId;
    private ObjectMapper objMapper;

    @BeforeEach
    void setUpI() throws Exception {

        existId = 1L;
        notExistId = 2L;
        dependenceId = 3L;
        objMapper = new ObjectMapper();
        productDto = Factory.createProductDto();
        page = new PageImpl<>(List.of(productDto));

        when(service.findAll(any())).thenReturn(page);

        when(service.findById(existId)).thenReturn(productDto);
        doThrow(ResorceNotFoundException.class).when(service).findById(notExistId);

        doNothing().when(service).delete(existId);
        doThrow(ResorceNotFoundException.class).when(service).delete(notExistId);
        doThrow(DataBaseException.class).when(service).delete(dependenceId);

        when(service.insert(productDto)).thenReturn(productDto);

        when(service.update(existId, productDto)).thenReturn(productDto);
        doThrow(ResorceNotFoundException.class).when(service).update(notExistId, productDto);

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

        verify(service).findById(notExistId);
    }

    @Test
    public void testDeleteVoid() throws Exception {
        mvc.perform(delete("/products/{id}", existId))
                .andExpect(status().isNoContent());

        verify(service).delete(existId);
    }

    @Test
    public void testDeleteThrowsResorceNotFoundException() throws Exception {
        mvc.perform(delete("/products/{id}", notExistId))
                .andExpect(status().isNotFound());
        verify(service).delete(notExistId);
    }

    @Test
    public void testDeleteThrowsDataBaseExcepition() throws Exception {
        mvc.perform(delete("/products/{id}", dependenceId))
                .andExpect(status().isBadRequest());

        verify(service).delete(dependenceId);
    }

    @Test
    public void testInsert() throws Exception {
        objMapper.registerModule(new JavaTimeModule());
        String jsonBody = objMapper.writeValueAsString(productDto);

        ResultActions result = mvc.perform(post("/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void testUpdate() throws Exception {
        objMapper.registerModule(new JavaTimeModule());
        String jsonBody = objMapper.writeValueAsString(productDto);

        ResultActions result = mvc.perform(put("/products/{id}", existId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void testThrowsResorceNotFoundException() throws Exception {
        objMapper.registerModule(new JavaTimeModule());
        String jsonBody = objMapper.writeValueAsString(productDto);

        ResultActions result = mvc.perform(put("/products/{id}", notExistId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

}
