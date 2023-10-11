package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.request.CategoryInsertDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.controllers.contract.CategoryService;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final ModelMapper mapper;


    public CategoryServiceImpl(CategoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> list = repository.findAll(pageable);
        return  list.map(CategoryDTO::new);
    }
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity Not Found "));
        return new CategoryDTO(category);
    }
    @Transactional
    public CategoryDTO insert(CategoryInsertDTO dto) {

        var entity =  mapper.map(dto,Category.class);
        entity = repository.save(entity);
        return mapper.map(entity, CategoryDTO.class);
    }
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
            Category entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found " + id));
            entity.setName(dto.getName());
            entity.setUpdatedAt(Instant.now());
            entity = repository.save(entity);
            return mapper.map(entity, CategoryDTO.class);


    }
    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Data Base Violation");
        }
    }

}
