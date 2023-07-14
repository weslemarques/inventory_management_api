package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.request.CategoryInsertDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.services.contract.CategoryService;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ModelMapper mapper;


    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> list = repository.findAll(pageable);
        return  list.map(c -> mapper.map(c, CategoryDTO.class));
    }
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category category = obj.orElseThrow(() -> new ResorceNotFoundException("Entity Not Found "));
        return mapper.map(category, CategoryDTO.class);
    }
    @Transactional
    public CategoryDTO insert(CategoryInsertDTO dto) {
        var entity =  mapper.map(dto,Category.class);
        entity = repository.save(entity);
        return mapper.map(entity, CategoryDTO.class);
    }
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.findById(id).orElseThrow(() -> new ResorceNotFoundException("Category not found " + id));
            entity.setName(dto.getName());
            entity.setUpdatedAt(Instant.now());
            entity = repository.save(entity);
            return mapper.map(entity, CategoryDTO.class);

        } catch (Exception e) {
             throw new RuntimeException("Não foi possivel processar sua solicitação");
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
