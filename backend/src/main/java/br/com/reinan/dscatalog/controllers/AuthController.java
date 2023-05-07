package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.UserLoginDTO;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenService tokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<String> authentication(@RequestBody UserLoginDTO login) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        Authentication authenticate = this.authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        var user = (User) authenticate.getPrincipal();

        String tokenJwt = tokenUtil.generateToken(user);
        System.out.println(tokenJwt);
        return ResponseEntity.ok(tokenJwt);
    }
}
