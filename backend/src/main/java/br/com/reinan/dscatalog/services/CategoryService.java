package br.com.reinan.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.reinan.dscatalog.dto.CategoryDto;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import jakarta.persistence.EntityExistsException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDto> findAllByPage(PageRequest pageable) {
        Page<Category> list = repository.findAll(pageable);
        return list.map(c -> new CategoryDto(c));
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
            @SuppressWarnings("deprecation")
            Category entity = repository.getOne(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDto(entity);
        } catch (EntityExistsException e) {
            throw new ResorceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResorceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Data Base Violation");
        }
    }

}
