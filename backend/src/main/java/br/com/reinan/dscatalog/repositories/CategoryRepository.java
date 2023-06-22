package br.com.reinan.dscatalog.repositories;

import br.com.reinan.dscatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
