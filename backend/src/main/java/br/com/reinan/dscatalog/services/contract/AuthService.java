package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.security.JwtResponse;

public interface AuthService {

    JwtResponse authentication(UserLoginDTO login);
}
