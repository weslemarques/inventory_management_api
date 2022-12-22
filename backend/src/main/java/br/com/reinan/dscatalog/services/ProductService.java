package br.com.reinan.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.reinan.dscatalog.dto.ProductDto;
import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import jakarta.persistence.EntityExistsException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDto> findAllByPage(PageRequest pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.map(c -> new ProductDto(c));
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {

        Optional<Product> obj = repository.findById(id);
        Product product = obj.orElseThrow(() -> new ResorceNotFoundException("Entity Not Found "));
        return new ProductDto(product);
    }

    @Transactional
    public ProductDto insert(ProductDto dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductDto(entity);

    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        try {
            @SuppressWarnings("deprecation")
            Product entity = repository.getOne(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new ProductDto(entity);
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
