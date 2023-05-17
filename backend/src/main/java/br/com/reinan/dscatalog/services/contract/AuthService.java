package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.security.TokenRefreshResponseDTO;

public interface AuthService {

    TokenRefreshResponseDTO authentication(UserLoginDTO login);
}
