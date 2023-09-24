package br.com.reinan.dscatalog.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "tb_user")
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void addRole(Role role){
        if(role != null) roles.add(role);
    }

    public void setFirstName(String firstName) {
        if (firstName != null)  this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName != null)  this.lastName = lastName;
    }

    public void setEmail(String email) {
        if (email != null)   this.email = email;
    }

    public void setPassword(String password) {
        if (password != null)  this.password = password;
    }

    @Override
    public Set<Role> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
