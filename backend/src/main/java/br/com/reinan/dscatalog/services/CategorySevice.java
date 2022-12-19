package br.com.reinan.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.reinan.dscatalog.entities.Category;
import br.com.reinan.dscatalog.repositories.CategoryRepository;

@Service
public class CategorySevice {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }
}
