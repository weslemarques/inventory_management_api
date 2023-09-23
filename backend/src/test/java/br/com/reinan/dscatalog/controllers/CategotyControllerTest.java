package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.request.CategoryInsertDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.security.filter.FilterToken;
import br.com.reinan.dscatalog.services.CategoryServiceImpl;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import br.com.reinan.dscatalog.util.factory.CategoryFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    private ObjectMapper objMapper;

    private Long dependenceId;
    PageImpl<CategoryDTO> page;
    private CategoryDTO categoryDTO;
    CategoryInsertDTO categoryInsertDTO;

    @BeforeEach
    void setUpI() {

        existId = 1L;
        notExistId = 2L;
        dependenceId = 3L;
        categoryDTO = CategoryFactory.createCategoryDto();
        objMapper = new ObjectMapper();
        categoryInsertDTO = CategoryFactory.requestCategory();
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

    @Test
    public void testFindById() throws Exception {
        when(service.findById(existId)).thenReturn(categoryDTO);
        ResultActions result =  mvc.perform(get("/v1/categories/{id}", existId))
                .andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").value("category"));
        verify(service).findById(existId);
    }

    @Test
    public void testFindByIdThrowsResorceNotFoundException() throws Exception {
        doThrow(ResourceNotFoundException.class).when(service).findById(notExistId);
        mvc.perform(get("/v1/categories/{id}", notExistId))
                .andExpect(status().isNotFound());


        verify(service).findById(notExistId);
    }

    @Test
    public void testDeleteVoid() throws Exception {
        doNothing().when(service).delete(existId);
        mvc.perform(delete("/v1/categories/{id}", existId))
                .andExpect(status().isNoContent());

        verify(service).delete(existId);
    }

    @Test
    public void testDeleteThrowsResorceNotFoundException() throws Exception {
        doThrow(ResourceNotFoundException.class).when(service).delete(notExistId);
        mvc.perform(delete("/v1/categories/{id}", notExistId))
                .andExpect(status().isNotFound());
        verify(service).delete(notExistId);
    }

    @Test
    public void testDeleteThrowsDataBaseExcepition() throws Exception {
        doThrow(DataBaseException.class).when(service).delete(dependenceId);
        mvc.perform(delete("/v1/categories/{id}", dependenceId))
                .andExpect(status().isBadRequest());

        verify(service).delete(dependenceId);
    }

    @Test
    public void testInsert() throws Exception {
        when(service.insert(any())).thenReturn(categoryDTO);
        objMapper.registerModule(new JavaTimeModule());
        String jsonBody = objMapper.writeValueAsString(categoryInsertDTO);

        ResultActions result = mvc.perform(post("/v1/categories")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.name").value("category"));
    }

    @Test
    public void testUpdate() throws Exception {
        when(service.update(any(Long.class), any(CategoryDTO.class))).thenReturn(categoryDTO);
        objMapper.registerModule(new JavaTimeModule());
        String jsonBody = objMapper.writeValueAsString(categoryDTO);

        ResultActions result = mvc.perform(put("/v1/categories/{id}", existId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").value("category"));
    }

    @Test
    public void updateThrowsResorceNotFoundException() throws Exception {
        doThrow(ResourceNotFoundException.class).when(service).update(any(Long.class), any(CategoryDTO.class));
        objMapper.registerModule(new JavaTimeModule());
        String jsonBody = objMapper.writeValueAsString(categoryDTO);

        ResultActions result = mvc.perform(put("/v1/categories/{id}", notExistId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

}
