package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.controllers.contract.StockMovementService;
import br.com.reinan.dscatalog.dto.response.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller("/stock")
public class StockMovementController {

    private StockMovementService service;


    public ResponseEntity<ProductDTO> sale(@PathVariable int amount,@PathVariable Long id){
        ProductDTO productDTO = service.sale(amount, id);
       return ResponseEntity.ok(productDTO);
    }


    public ResponseEntity<ProductDTO> addStock(int amount, Long id){
        ProductDTO productDTO = service.addStock(amount, id);
        return ResponseEntity.ok(productDTO);
    }

}
