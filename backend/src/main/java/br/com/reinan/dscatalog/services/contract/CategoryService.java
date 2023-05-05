package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    public Page<CategoryDTO> findAll(Pageable pageable);
    public CategoryDTO findById(Long id);
    public CategoryDTO insert(CategoryDTO dto);
    public CategoryDTO update(Long id, CategoryDTO dto);
    public void delete(Long id);
}
