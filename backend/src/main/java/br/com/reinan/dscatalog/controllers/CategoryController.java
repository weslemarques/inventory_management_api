package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.request.CategoryInsertDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.services.CategoryServiceImpl;
import br.com.reinan.dscatalog.services.contract.CategoryService;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/categories")
@SecurityRequirement(name = "Bearer ")
public class CategoryController {

    @Autowired
    private CategoryService service;


    public CategoryController(CategoryService  service){
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Access the logged user informations", security = {@SecurityRequirement(name = "bearer")})
    public ResponseEntity<Page<CategoryDTO>> findAll(@ParameterObject Pageable pageable) {
        Page<CategoryDTO> list = service.findAll(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO category = service.findById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryInsertDTO request) {
        CategoryDTO responseCategory = service.insert(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseCategory.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseCategory);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        dto = service.update(id, dto);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Esse recurso esta atrelado a outro recurso");
        }
        return ResponseEntity.noContent().build();
    }
}
