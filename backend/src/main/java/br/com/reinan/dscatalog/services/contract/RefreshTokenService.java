package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.entities.RefreshToken;

public interface RefreshTokenService {
    public String findByToken(String token);

    public String createRefreshToken(Long userId);

    public String verifyExpiration(RefreshToken refreshToken);
}
