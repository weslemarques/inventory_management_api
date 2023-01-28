package br.com.reinan.dscatalog.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.reinan.dscatalog.dto.RoleDTO;
import br.com.reinan.dscatalog.dto.UserDTO;
import br.com.reinan.dscatalog.entities.Role;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.UserRepository;
import br.com.reinan.dscatalog.repositories.RoleRepository;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> list = repository.findAll(pageable);
        return list.map(c -> new UserDTO(c));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResorceNotFoundException("Entity Not Found "));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            Optional<User> obj = repository.findById(id);
            User entity = obj.get();
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        } catch (NoSuchElementException e) {
            throw new ResorceNotFoundException("Id not found " + id);
        }
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

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();

        for (RoleDTO roleDto : dto.getRoles()) {
            Optional<Role> obj = roleRepository.findById(roleDto.getId());
            Role role = obj.get();
            entity.getRoles().add(role);
        }
    }

}
