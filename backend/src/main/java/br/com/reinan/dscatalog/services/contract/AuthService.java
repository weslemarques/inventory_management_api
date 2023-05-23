package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.securityDtos.JwtResponse;
import br.com.reinan.dscatalog.dto.securityDtos.TokenRefreshRequest;
import br.com.reinan.dscatalog.dto.securityDtos.TokenRefreshResponse;

public interface AuthService {

    JwtResponse authentication(UserLoginDTO login);
    TokenRefreshResponse refreshToken(TokenRefreshRequest refreshToken);
}
