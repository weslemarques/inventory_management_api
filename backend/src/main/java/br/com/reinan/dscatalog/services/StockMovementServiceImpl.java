package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.services.contract.StockMovementService;

import java.util.List;

public class StockMovementServiceImpl implements StockMovementService {
    @Override
    public boolean productSale(List<Long> products) {
        return false;
    }

    @Override
    public void addStock(int amount, Long id) {

    }
}
