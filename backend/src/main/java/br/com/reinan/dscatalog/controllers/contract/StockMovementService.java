package br.com.reinan.dscatalog.controllers.contract;

import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.entities.Order;
import br.com.reinan.dscatalog.entities.OrderItem;

import java.util.List;

public interface StockMovementService {

    List<OrderItem> sale(Order order);
    ProductDTO addStock(int amount, Long id);
}
