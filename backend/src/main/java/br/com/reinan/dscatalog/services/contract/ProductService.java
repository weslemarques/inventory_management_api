package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.request.ProductRequestDTO;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDTO> findAll(Pageable pageable);
    ProductDTO findById(Long id);
    ProductDTO insert(ProductRequestDTO dto);
    ProductDTO update(Long id, ProductRequestDTO dto);
    void delete(Long id);
}
