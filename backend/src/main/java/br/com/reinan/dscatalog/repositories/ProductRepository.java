package br.com.reinan.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reinan.dscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
