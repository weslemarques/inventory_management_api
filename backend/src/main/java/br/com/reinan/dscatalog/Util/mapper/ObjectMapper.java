package br.com.reinan.dscatalog.Util.mapper;

import br.com.reinan.dscatalog.dto.response.ProductDTO;
import br.com.reinan.dscatalog.entities.Product;

public class ObjectMapper {

    public static Product toEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    public static ProductDTO toDto(Product entity){
        return  new ProductDTO(entity);
    }


}
