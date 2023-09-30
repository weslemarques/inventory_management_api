package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.response.UserDTO;
import br.com.reinan.dscatalog.dto.request.UserRequestDTO;
import br.com.reinan.dscatalog.dto.request.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    Page<UserDTO> findAll(Pageable pageable);
    UserDTO findById(Long id);
    UserDTO insert(UserRequestDTO dto) ;
    UserDTO update(Long id, UserUpdateDTO dto);
    void delete(Long id) ;
}
