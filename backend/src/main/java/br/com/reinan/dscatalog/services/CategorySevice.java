package br.com.reinan.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.reinan.dscatalog.dto.CategoryDto;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import jakarta.persistence.EntityExistsException;

@Service
public class CategorySevice {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(c -> new CategoryDto(c)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {

        Optional<Category> obj = repository.findById(id);
        Category category = obj.orElseThrow(() -> new ResorceNotFoundException("Entity Not Found "));
        return new CategoryDto(category);
    }

    @Transactional
    public CategoryDto insert(CategoryDto dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDto(entity);

    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {
        try {
            Category entity = repository.getOne(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDto(entity);
        } catch (EntityExistsException e) {
            throw new ResorceNotFoundException("Id not found " + id);
        }
    }

}
