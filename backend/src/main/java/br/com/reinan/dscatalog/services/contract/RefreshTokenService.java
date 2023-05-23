package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken refreshToken);

    void detele(RefreshToken refreshToken);

}
