package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.controllers.contract.StockMovementService;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.entities.Order;
import br.com.reinan.dscatalog.entities.OrderItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/stock")
public class StockMovementController {

    private final StockMovementService service;

    public StockMovementController(StockMovementService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<List<OrderItem>> sale(@RequestBody Order order){
        List<OrderItem> orderItemList = service.sale(order);
       return ResponseEntity.ok(orderItemList);
    }


    @PatchMapping("add/{id}")
    public ResponseEntity<ProductDTO> addStock(@RequestBody int amount, @PathVariable Long id){
        ProductDTO productDTO = service.addStock(amount, id);
        return ResponseEntity.ok(productDTO);
    }

}
