package br.com.reinan.dscatalog.dto.response;

import br.com.reinan.dscatalog.dto.base.UserBaseDTO;
import br.com.reinan.dscatalog.entities.User;

import java.io.Serial;
import java.io.Serializable;

public class UserDTO extends UserBaseDTO implements Serializable  {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public UserDTO(User entity) {
        this.setFirstName(entity.getFirstName());
        setLastName(entity.getLastName());
        setEmail(entity.getEmail());
        setId(entity.getId());
        entity.getRoles().forEach(role -> this.getRoles().add(new RoleDTO(role)));
    }

    public UserDTO( String firstName, String lastName, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }




}
