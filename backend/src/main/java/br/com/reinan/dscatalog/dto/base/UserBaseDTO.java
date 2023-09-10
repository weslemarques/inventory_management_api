package br.com.reinan.dscatalog.dto.base;

import br.com.reinan.dscatalog.dto.response.RoleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public  abstract class UserBaseDTO {

    private String firstName;
    private String lastName;
    private String email;

    private Set<RoleDTO> roles = new HashSet<>();


}
