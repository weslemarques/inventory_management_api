package br.com.reinan.dscatalog.controlles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.reinan.dscatalog.dto.CategoryDto;
import br.com.reinan.dscatalog.services.CategorySevice;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategorySevice service;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll() {
        List<CategoryDto> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        CategoryDto category = service.findById(id);
        return ResponseEntity.ok(category);
    }
}
