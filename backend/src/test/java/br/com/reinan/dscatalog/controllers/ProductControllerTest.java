package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.request.ProductRequestDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.security.filter.FilterToken;
import br.com.reinan.dscatalog.services.ProductServiceImpl;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import br.com.reinan.dscatalog.util.factory.ProductFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class ProductControllerTest {


    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductServiceImpl service;

    private ProductDTO productDto;

    private Long existId;
    @MockBean
    FilterToken filterToken;
    private Long notExistId;
    private Long dependenceId;
    PageImpl<ProductDTO> page;
    private ObjectMapper objMapper;
    ProductRequestDTO requestDTO;

    @BeforeEach
    void setUpI() {

        existId = 1L;
        notExistId = 1000L;
        dependenceId = 3L;
        objMapper = new ObjectMapper();
        productDto = ProductFactory.createProductDto();
        requestDTO = ProductFactory.createProductRequest();
        page = new PageImpl<>(List.of(productDto));
    }

    @Test
    public void testFindAll() throws Exception {

        when(service.findAll(any())).thenReturn(page);
        ResultActions page1 = mvc.perform(get("/v1/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindById() throws Exception {
        when(service.findById(existId)).thenReturn(productDto);
        mvc.perform(get("/v1/products/{id}", existId))
                .andExpect(status().isOk());

        verify(service).findById(existId);
    }

    @Test
    public void testFindByIdThrowsResorceNotFoundException() throws Exception {
        doThrow(ResourceNotFoundException.class).when(service).findById(notExistId);
        mvc.perform(get("/v1/products/{id}", notExistId))
                .andExpect(status().isNotFound());

        verify(service).findById(notExistId);
    }

    @Test
    public void testDeleteVoid() throws Exception {
        doNothing().when(service).delete(existId);
        mvc.perform(delete("/v1/products/{id}", existId))
                .andExpect(status().isNoContent());

        verify(service).delete(existId);
    }

    @Test
    public void testDeleteThrowsResorceNotFoundException() throws Exception {
        doThrow(ResourceNotFoundException.class).when(service).delete(notExistId);
        mvc.perform(delete("/v1/products/{id}", notExistId))
                .andExpect(status().isNotFound());
        verify(service).delete(notExistId);
    }

    @Test
    public void testDeleteThrowsDataBaseExcepition() throws Exception {
        doThrow(DataBaseException.class).when(service).delete(dependenceId);
        mvc.perform(delete("/v1/products/{id}", dependenceId))
                .andExpect(status().isBadRequest());

        verify(service).delete(dependenceId);
    }

    @Test
    public void testInsert() throws Exception {
        when(service.insert(any())).thenReturn(productDto);
        objMapper.registerModule(new JavaTimeModule());
        String jsonBody = objMapper.writeValueAsString(requestDTO);

        ResultActions result = mvc.perform(post("/v1/products")
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
        when(service.update(existId, requestDTO)).thenReturn(productDto);
        objMapper.registerModule(new JavaTimeModule());
        String jsonBody = objMapper.writeValueAsString(requestDTO);

        ResultActions result = mvc.perform(put("/v1/products/{id}", existId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void updateThrowsResorceNotFoundException() throws Exception {
        doThrow(ResourceNotFoundException.class).when(service).update(notExistId, requestDTO);
        objMapper.registerModule(new JavaTimeModule());
        String jsonBody = objMapper.writeValueAsString(requestDTO);

        ResultActions result = mvc.perform(put("/v1/products/{id}", notExistId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

}
