package br.com.reinan.dscatalog.dto.request;

import br.com.reinan.dscatalog.dto.base.UserBaseDTO;
import br.com.reinan.dscatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;

@UserInsertValid
public class UserRequestDTO extends UserBaseDTO {

    @NotBlank
    private String password;

    public UserRequestDTO() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
