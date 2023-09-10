package br.com.reinan.dscatalog.util.factory;

import br.com.reinan.dscatalog.entities.Role;
import br.com.reinan.dscatalog.entities.User;

public class UserFactory {

    public static User createUserAdmin(){
        User userAdmin = new User("firstName", "lastName", "email", "password");

        userAdmin.getAuthorities().add(new Role("ROLE_OPERATOR"));
        userAdmin.getAuthorities().add(new Role("ROLE_ADMIN"));

        return userAdmin;
    }

}
