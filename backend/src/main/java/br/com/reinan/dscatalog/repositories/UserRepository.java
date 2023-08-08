package br.com.reinan.dscatalog.repositories;

import br.com.reinan.dscatalog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByFirstName(String firstName);
    Boolean existsByEmail(String email);
}
