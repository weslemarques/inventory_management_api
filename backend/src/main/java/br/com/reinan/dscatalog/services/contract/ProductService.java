package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public Page<ProductDTO> findAll(Pageable pageable);
    public ProductDTO findById(Long id);
    public ProductDTO insert(ProductDTO dto);
    public ProductDTO update(Long id, ProductDTO dto);
    public void delete(Long id);
}
