package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.UserLoginDTO;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.services.contract.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenUtil;
    @Override
    public String authentication(UserLoginDTO login) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        Authentication authenticate = this.authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        var user = (User) authenticate.getPrincipal();
        List<String> roles = user.

        return tokenUtil.generateToken(user, user.getAuthorities());
    }
}
