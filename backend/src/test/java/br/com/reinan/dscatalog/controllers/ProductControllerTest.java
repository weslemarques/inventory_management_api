package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.request.ProductRequestDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.services.ProductServiceImpl;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import br.com.reinan.dscatalog.util.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
public class ProductControllerTest {
//    @Autowired
//    private MockMvc mvc;
//    @MockBean
//    private ProductServiceImpl service;
//
//    private ProductDTO productDto;
//    private PageImpl<ProductDTO> page;
//
//    private ProductRequestDTO requestDTO;
//    private Long existId;
//    private Long notExistId;
//    private Long dependenceId;
//    private ObjectMapper objMapper;
//
//    @BeforeEach
//    void setUpI() throws Exception {
//
//        existId = 1L;
//        notExistId = 2L;
//        dependenceId = 3L;
//        objMapper = new ObjectMapper();
//        productDto = Factory.createProductDto();
//        requestDTO = Factory.createProductRequest();
//        page = new PageImpl<>(List.of(productDto));
//
//        when(service.findAll(any())).thenReturn(page);
//
//        when(service.findById(existId)).thenReturn(productDto);
//        doThrow(ResourceNotFoundException.class).when(service).findById(notExistId);
//
//        doNothing().when(service).delete(existId);
//        doThrow(ResourceNotFoundException.class).when(service).delete(notExistId);
//        doThrow(DataBaseException.class).when(service).delete(dependenceId);
//
//        when(service.insert(requestDTO)).thenReturn(productDto);
//
//        when(service.update(existId, requestDTO)).thenReturn(productDto);
//        doThrow(ResourceNotFoundException.class).when(service).update(notExistId, requestDTO);
//
//    }
//
//    @Test
//    public void testFindAll() throws Exception {
//        mvc.perform(get("/v1/products")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        verify(service).findAll(any(Pageable.class));
//    }
//
//    @Test
//    public void testFindById() throws Exception {
//        mvc.perform(get("/v1/products/{id}", existId))
//                .andExpect(status().isOk());
//
//        verify(service).findById(existId);
//    }
//
//    @Test
//    public void testFindByIdThrowsResorceNotFoundException() throws Exception {
//        mvc.perform(get("/v1/products/{id}", notExistId))
//                .andExpect(status().isNotFound());
//
//        verify(service).findById(notExistId);
//    }
//
//    @Test
//    public void testDeleteVoid() throws Exception {
//        mvc.perform(delete("/v1/products/{id}", existId))
//                .andExpect(status().isNoContent());
//
//        verify(service).delete(existId);
//    }
//
//    @Test
//    public void testDeleteThrowsResorceNotFoundException() throws Exception {
//        mvc.perform(delete("/v1/products/{id}", notExistId))
//                .andExpect(status().isNotFound());
//        verify(service).delete(notExistId);
//    }
//
//    @Test
//    public void testDeleteThrowsDataBaseExcepition() throws Exception {
//        mvc.perform(delete("/v1/products/{id}", dependenceId))
//                .andExpect(status().isBadRequest());
//
//        verify(service).delete(dependenceId);
//    }
//
//    @Test
//    public void testInsert() throws Exception {
//        objMapper.registerModule(new JavaTimeModule());
//        String jsonBody = objMapper.writeValueAsString(productDto);
//
//        ResultActions result = mvc.perform(post("/v1/products")
//                .content(jsonBody)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        result.andExpect(status().isCreated());
//        result.andExpect(jsonPath("$.price").exists());
//        result.andExpect(jsonPath("$.name").exists());
//        result.andExpect(jsonPath("$.description").exists());
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        objMapper.registerModule(new JavaTimeModule());
//        String jsonBody = objMapper.writeValueAsString(productDto);
//
//        ResultActions result = mvc.perform(put("/v1/products/{id}", existId)
//                .content(jsonBody)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        result.andExpect(status().isOk());
//        result.andExpect(jsonPath("$.price").exists());
//        result.andExpect(jsonPath("$.name").exists());
//        result.andExpect(jsonPath("$.description").exists());
//    }
//
//    @Test
//    public void testThrowsResorceNotFoundException() throws Exception {
//        objMapper.registerModule(new JavaTimeModule());
//        String jsonBody = objMapper.writeValueAsString(productDto);
//
//        ResultActions result = mvc.perform(put("/v1/products/{id}", notExistId)
//                .content(jsonBody)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        result.andExpect(status().isNotFound());
//    }

}
