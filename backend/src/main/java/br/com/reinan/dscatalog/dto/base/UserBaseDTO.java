package br.com.reinan.dscatalog.dto.base;

import br.com.reinan.dscatalog.dto.response.RoleDTO;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public  abstract class UserBaseDTO {

    private String firstName;
    private String lastName;
    @Email(message = "Digite um email válido")
    private String email;

    private Set<RoleDTO> roles = new HashSet<>();


}
