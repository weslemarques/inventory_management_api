package br.com.reinan.dscatalog.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;

@Entity
@Data
@Table(name = "refreshToken")
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;


    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    public RefreshToken() {
    }
}
