package br.com.reinan.dscatalog.dto.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class TokenRefreshRequestDTO implements Serializable {


    @NotBlank
    private String refreshToken;

}
