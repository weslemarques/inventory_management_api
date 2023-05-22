package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.entities.RefreshToken;
import br.com.reinan.dscatalog.services.contract.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Override
    public String findByToken(String token) {
        return null;
    }

    @Override
    public String createRefreshToken(Long userId) {
        return null;
    }

    @Override
    public String verifyExpiration(RefreshToken refreshToken) {
        return null;
    }
}
