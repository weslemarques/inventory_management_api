package br.com.reinan.dscatalog.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Entity
@Table(name = "tb_order")
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private Set<OrderItem> items;

    public Order() {
    }


    public void addItem(OrderItem  item){
        items.add(item);
    }
}
