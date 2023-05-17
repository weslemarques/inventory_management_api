package br.com.reinan.dscatalog.dto.security;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class TokenRefreshRequestDTO implements Serializable {


    private String refreshToken;

}
