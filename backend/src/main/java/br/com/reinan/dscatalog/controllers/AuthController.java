package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.UserLoginDTO;
import br.com.reinan.dscatalog.services.contract.AuthService;
import br.com.reinan.dscatalog.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> authentication(@RequestBody UserLoginDTO login) {

        String tokenJwt = authService.authentication(login);
        return ResponseEntity.ok(tokenJwt);
    }
}
