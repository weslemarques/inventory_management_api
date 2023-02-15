package br.com.reinan.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reinan.dscatalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
