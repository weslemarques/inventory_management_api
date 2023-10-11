package br.com.reinan.dscatalog.controllers.contract;

import br.com.reinan.dscatalog.dto.response.ProductDTO;

import java.util.List;

public interface StockMovementService {

    ProductDTO sale(int amount, Long id);
    ProductDTO addStock(int amount, Long id);
}
