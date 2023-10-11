package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.controllers.contract.StockMovementService;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    private final ProductRepository productRepository;

    public StockMovementServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductDTO sale(int amount, Long id) {
            Product product = productRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Product Not Found"));
            product.setStock(product.getStock() - amount);
        productRepository.flush();
        return new ProductDTO(product);
    }

    @Override
    @Transactional
    public ProductDTO addStock(int amount, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product Not Found"));
        product.setStock(product.getStock() + amount);
        productRepository.flush();
        return new ProductDTO(product);
    }
}
