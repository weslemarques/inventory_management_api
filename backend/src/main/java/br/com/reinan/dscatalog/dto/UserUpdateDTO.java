package br.com.reinan.dscatalog.dto;

import br.com.reinan.dscatalog.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO {

    UserUpdateDTO() {
        super();
    }
}
