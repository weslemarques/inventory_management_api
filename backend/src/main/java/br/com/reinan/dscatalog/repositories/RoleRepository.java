package br.com.reinan.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reinan.dscatalog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
