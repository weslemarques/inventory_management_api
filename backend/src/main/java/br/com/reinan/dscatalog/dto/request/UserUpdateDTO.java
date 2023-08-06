package br.com.reinan.dscatalog.dto.request;

import br.com.reinan.dscatalog.dto.base.UserBaseDTO;
import br.com.reinan.dscatalog.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends UserBaseDTO {

    UserUpdateDTO() {
        super();
    }
}
