package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.Util.mapper.ObjectMapper;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.services.contract.ProductService;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {

        Product entity = repository.findById(id).orElseThrow(() -> new ResorceNotFoundException("Entity Not Found "));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        ObjectMapper.toEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {

        Product entity = repository.findById(id)
                .orElseThrow(() -> new ResorceNotFoundException("Id not found " + id));
        ObjectMapper.toEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
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
            Category category = categoryRepository.findById(cat.getId()).get();
            entity.getCategories().add(category);
        }
    }

}
