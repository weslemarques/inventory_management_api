package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.base.UserBaseDTO;
import br.com.reinan.dscatalog.dto.request.UserRequestDTO;
import br.com.reinan.dscatalog.dto.request.UserUpdateDTO;
import br.com.reinan.dscatalog.dto.response.RoleDTO;
import br.com.reinan.dscatalog.dto.response.UserDTO;
import br.com.reinan.dscatalog.entities.Role;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.RoleRepository;
import br.com.reinan.dscatalog.repositories.UserRepository;
import br.com.reinan.dscatalog.controllers.contract.UserService;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements  UserService {

    private final BCryptPasswordEncoder passwordEncoder;

     private final ModelMapper mapper;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found "));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserRequestDTO dto) {
        User entity = new User();
        mapper.map(dto, entity);
        copyDtoListToEntityList(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
           User  entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
            mapper.map(dto,entity);
            entity = userRepository.save(entity);
            return mapper.map(entity,UserDTO.class);
    }

    @Transactional
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Data Base Violation");
        }
    }

    private void copyDtoListToEntityList(UserBaseDTO dto, User entity) {
        entity.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.findById(roleDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Role not found" + roleDto.getId()));
            entity.getRoles().add(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email address " + email));

    }

}
