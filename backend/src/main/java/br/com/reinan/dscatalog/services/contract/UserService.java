package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.UserDTO;
import br.com.reinan.dscatalog.dto.UserInsertDTO;
import br.com.reinan.dscatalog.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    public Page<UserDTO> findAll(Pageable pageable);
    public UserDTO findById(Long id);
    public UserDTO insert(UserInsertDTO dto) ;
    public UserDTO update(Long id, UserUpdateDTO dto);
    public void delete(Long id) ;
}
