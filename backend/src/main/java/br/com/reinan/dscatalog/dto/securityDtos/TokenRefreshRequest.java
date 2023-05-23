package br.com.reinan.dscatalog.dto.securityDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TokenRefreshRequest implements Serializable {

    @NotBlank
    private String refreshToken;

}
