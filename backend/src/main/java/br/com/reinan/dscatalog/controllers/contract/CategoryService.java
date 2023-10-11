package br.com.reinan.dscatalog.controllers.contract;

import br.com.reinan.dscatalog.dto.request.CategoryInsertDTO;
import br.com.reinan.dscatalog.dto.response.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryDTO> findAll(Pageable pageable);
    CategoryDTO findById(Long id);
    CategoryDTO insert(CategoryInsertDTO dto);
    CategoryDTO update(Long id, CategoryDTO dto);
    void delete(Long id);
}
