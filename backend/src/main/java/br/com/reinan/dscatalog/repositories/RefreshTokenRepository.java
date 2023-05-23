package br.com.reinan.dscatalog.repositories;

import br.com.reinan.dscatalog.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    public Optional<RefreshToken> findByToken(String token);


}
