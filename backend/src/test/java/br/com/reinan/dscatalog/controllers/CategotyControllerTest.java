package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.services.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.service.invoker.HttpRequestValues;

import java.net.http.HttpRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@ActiveProfiles("test")
@ComponentScan("br.com.reinan.dscatalog.config.AppConfig")
public class CategotyControllerTest {

//    @Autowired
//    private MockMvc mvc;
//    @MockBean
//    private CategoryServiceImpl service;
//
//    @Test
//    public void testFindAll() throws Exception {
//        mvc.perform(get("/v1/categories")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        verify(service).findAll(any(Pageable.class));
//    }

}
