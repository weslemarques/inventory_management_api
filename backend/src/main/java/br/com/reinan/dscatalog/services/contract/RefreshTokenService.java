package br.com.reinan.dscatalog.services.contract;

public interface RefreshTokenService {
    public String findByToken(String token);
    public String createRefreshToken(User token);
    public String findByToken(String token);
}
