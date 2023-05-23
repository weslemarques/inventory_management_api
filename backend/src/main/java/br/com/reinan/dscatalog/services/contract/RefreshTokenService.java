package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    public Optional<RefreshToken> findByToken(String token);

    public RefreshToken createRefreshToken(Long userId);

    public RefreshToken verifyExpiration(RefreshToken refreshToken);

    public void detele(RefreshToken refreshToken);

    public  void deleteByToken(String token);
}
