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

import br.com.reinan.dscatalog.dto.UserDto;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.UserRepository;
import br.com.reinan.dscatalog.services.exceptions.DataBaseException;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> list = repository.findAll(pageable);
        return list.map(c -> new UserDto(c));
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow(() -> new ResorceNotFoundException("Entity Not Found "));
        return new UserDto(entity);
    }

    @Transactional
    public UserDto insert(UserDto dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        return new UserDto(entity);
    }

    @Transactional
    public UserDto update(Long id, UserDto dto) {
        try {
            Optional<User> obj = repository.findById(id);
            User entity = obj.get();
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDto(entity);
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

    private void copyDtoToEntity(UserDto dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

    }

}
