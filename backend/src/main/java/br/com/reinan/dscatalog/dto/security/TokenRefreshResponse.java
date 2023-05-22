package br.com.reinan.dscatalog.dto.security;

import java.io.Serializable;

public class TokenRefreshResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private String type = "Bearer";

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
