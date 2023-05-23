package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.securityDtos.JwtResponse;

public interface AuthService {

    JwtResponse authentication(UserLoginDTO login);
}
