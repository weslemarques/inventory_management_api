package br.com.reinan.dscatalog.dto.request;

import br.com.reinan.dscatalog.dto.base.ProductBaseDTO;

import java.io.Serializable;
import java.time.Instant;

public class ProductRequestDTO extends ProductBaseDTO implements Serializable {

    public ProductRequestDTO() {
        super();
    }

    public ProductRequestDTO(String name, Double price, Instant date, String description, String imgUrl) {
        super(name, price, date, description, imgUrl);
    }
}
