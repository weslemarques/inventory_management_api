package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.entities.Product;
import br.com.reinan.dscatalog.repositories.ProductRepository;
import br.com.reinan.dscatalog.services.contract.StockMovementService;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    private final ProductRepository productRepository;

    public StockMovementServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean sale(int amount, Long id) {
            Product product = productRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Product Not Found"));
            product.setStock(product.getStock() - amount);
            return true;
    }

    @Override
    public void addStock(int amount, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product Not Found"));
        product.setStock(product.getStock() + amount);
    }
}
