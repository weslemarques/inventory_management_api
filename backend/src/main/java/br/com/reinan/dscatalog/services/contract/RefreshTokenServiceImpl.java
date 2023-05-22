package br.com.reinan.dscatalog.services.contract;

public interface RefreshTokenServiceImpl {
    public String findByToken(String token);
}
