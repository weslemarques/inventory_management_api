package br.com.reinan.dscatalog.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tb_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;
    private int amount;

    public OrderItem(Long id, Product product, int amount) {
        this.id = id;
        this.product = product;
        this.amount = amount;
    }


    public double getFullAmount(){
       return  product.getPrice() * this.amount;
    }

    public double getProductValue(){
        return  product.getPrice();
    }

    public OrderItem() {

    }

    public void setProduct(Product product) {
        if (product != null)this.product = product;
    }

    public void setAmount(int amount) {
         this.amount = amount;
    }
}
