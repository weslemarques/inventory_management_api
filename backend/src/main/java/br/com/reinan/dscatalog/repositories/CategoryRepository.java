package br.com.reinan.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reinan.dscatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
