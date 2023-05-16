package br.com.reinan.dscatalog.dto.request;

import br.com.reinan.dscatalog.dto.response.UserDTO;
import br.com.reinan.dscatalog.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO {

    UserUpdateDTO() {
        super();
    }
}
