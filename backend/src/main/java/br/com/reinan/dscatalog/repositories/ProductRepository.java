package br.com.reinan.dscatalog.repositories;

import br.com.reinan.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
