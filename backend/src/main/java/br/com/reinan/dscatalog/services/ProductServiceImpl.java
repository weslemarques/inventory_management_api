package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.request.ProductRequestDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.repositories.CategoryRepository;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.controllers.contract.ProductService;
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
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    CategoryRepository categoryRepository;

    private final ModelMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ModelMapper mapper,CategoryRepository categoryRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {

        Product entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity Not Found "));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductRequestDTO dto) {
        Product product = mapper.map(dto, Product.class);
        addCategories(dto.getCategories(),product);
        repository.save(product);
        return mapper.map(product, ProductDTO.class);
    }

    @Transactional
    public ProductDTO update(Long id, ProductRequestDTO dto) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
        mapper.map(dto, entity);
        entity.setUpdatedAt(Instant.now());
        entity = repository.save(entity);
        return new ProductDTO(entity, entity.getCategories());
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

    private void addCategories(List<CategoryDTO> listCatDTO, Product entity){
        for (CategoryDTO catDTO: listCatDTO) {
            Category cat = categoryRepository.findById(catDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoy not Found"));
            entity.addCategory(cat);
        }
    }

}
