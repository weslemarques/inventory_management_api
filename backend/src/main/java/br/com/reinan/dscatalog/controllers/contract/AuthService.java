package br.com.reinan.dscatalog.controllers.contract;

import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.response.JwtResponse;
import br.com.reinan.dscatalog.dto.request.TokenRefreshRequest;
import br.com.reinan.dscatalog.dto.response.TokenRefreshResponse;   

public interface AuthService {

    JwtResponse authentication(UserLoginDTO login);
    TokenRefreshResponse refreshToken(TokenRefreshRequest refreshToken);
}
