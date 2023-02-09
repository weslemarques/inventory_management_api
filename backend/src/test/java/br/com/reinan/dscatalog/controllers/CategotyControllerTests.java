package br.com.reinan.dscatalog.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.reinan.dscatalog.services.CategoryService;

@WebMvcTest(CategoryController.class)
public class CategotyControllerTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CategoryService service;

    @BeforeEach
    private void setUp(){
        when(service.findAll(any())).thenReturn(any());

    }

    @Test
    public void testFindAll() throws Exception {

        mvc.perform(get("/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
