package br.com.reinan.dscatalog.dto.request;

import br.com.reinan.dscatalog.dto.response.UserDTO;
import br.com.reinan.dscatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;

@UserInsertValid
public class UserInsertDTO extends UserDTO {

    @NotBlank
    private String password;

    public UserInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
