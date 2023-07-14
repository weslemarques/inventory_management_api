package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.response.UserDTO;
import br.com.reinan.dscatalog.dto.request.UserInsertDTO;
import br.com.reinan.dscatalog.dto.request.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    public Page<UserDTO> findAll(Pageable pageable);
    public UserDTO findById(Long id);
    public UserDTO insert(UserInsertDTO dto) ;
    public UserDTO update(Long id, UserUpdateDTO dto);
    public void delete(Long id) ;
}
