package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.security.TokenRefreshDTO;

public interface AuthService {

    TokenRefreshDTO authentication(UserLoginDTO login);
}
