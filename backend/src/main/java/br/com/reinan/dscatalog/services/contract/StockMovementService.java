package br.com.reinan.dscatalog.services.contract;

import java.util.List;

public interface StockMovementService {

    boolean productSale(List<Long> products);
    void addStock(int amount, Long id);
}
