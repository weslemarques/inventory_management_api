package br.com.reinan.dscatalog.dto.securityDtos;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class TokenRefreshResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private String type = "Bearer";

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
