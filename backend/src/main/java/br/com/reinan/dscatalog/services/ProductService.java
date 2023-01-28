package br.com.reinan.dscatalog.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.reinan.dscatalog.dto.CategoryDTO;
import br.com.reinan.dscatalog.dto.ProductDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.map(c -> new ProductDTO(c));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {

        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResorceNotFoundException("Entity Not Found "));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);

    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Optional<Product> obj = repository.findById(id);
            Product entity = obj.get();
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (NoSuchElementException e) {
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

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());

        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for (CategoryDTO cat : dto.getCategories()) {
            @SuppressWarnings("deprecation")
            Category category = categoryRepository.getOne(cat.getId());
            entity.getCategories().add(category);
        }
    }

}
