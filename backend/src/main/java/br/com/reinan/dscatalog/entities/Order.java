package br.com.reinan.dscatalog.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
