package br.com.reinan.dscatalog.dto.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenRefreshResponseDTO implements Serializable {


        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";

        public TokenRefreshResponseDTO(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
}
