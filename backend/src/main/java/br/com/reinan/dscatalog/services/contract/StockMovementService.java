package br.com.reinan.dscatalog.services.contract;

import java.util.List;

public interface StockMovementService {

    boolean sale(int amount, Long id);
    void addStock(int amount, Long id);
}
