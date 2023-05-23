package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.entities.RefreshToken;
import br.com.reinan.dscatalog.repositories.RefreshTokenRepository;
import br.com.reinan.dscatalog.repositories.UserRepository;
import br.com.reinan.dscatalog.services.contract.RefreshTokenService;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import br.com.reinan.dscatalog.services.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Value("${reinan.jwt.refreshTokenExpirationMs}")
    private Long refreshTokenExpirationMs;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public  RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).orElseThrow(() -> new ResorceNotFoundException("Id not foud")));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new TokenExpiredException("Esse token está expirado, Faça login novamente");
        }

        return refreshToken;
    }

    @Override
    public void detele(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

}
