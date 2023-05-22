package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.response.RoleDTO;
import br.com.reinan.dscatalog.dto.response.UserDTO;
import br.com.reinan.dscatalog.dto.request.UserInsertDTO;
import br.com.reinan.dscatalog.dto.request.UserUpdateDTO;
import br.com.reinan.dscatalog.entities.Role;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.RoleRepository;
import br.com.reinan.dscatalog.repositories.UserRepository;
import br.com.reinan.dscatalog.services.contract.UserService;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepository repository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = repository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResorceNotFoundException("Entity Not Found "));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            Optional<User> obj = userRepository.findById(id);
            User entity = obj.get();
            copyDtoToEntity(dto, entity);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        } catch (NoSuchElementException e) {
            throw new ResorceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email address " + email));

    }

}
