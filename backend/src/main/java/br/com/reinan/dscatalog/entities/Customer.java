package br.com.reinan.dscatalog.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "tb_customer")
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fistName;
    @Column(nullable = false)
    private String lastName;
    private String email;
    @Column(unique = true, nullable = false)
    private String cpf;

}
