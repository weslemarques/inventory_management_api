package br.com.reinan.dscatalog.services.contract;

import br.com.reinan.dscatalog.dto.UserLoginDTO;

public interface AuthService {

    String authentication(UserLoginDTO login);
}
