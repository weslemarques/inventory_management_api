package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.securityDtos.JwtResponse;
import br.com.reinan.dscatalog.dto.securityDtos.TokenRefreshRequest;
import br.com.reinan.dscatalog.dto.securityDtos.TokenRefreshResponse;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.security.jwt.JwtUtils;
import br.com.reinan.dscatalog.services.contract.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authentication(@Valid @RequestBody UserLoginDTO login) {

        JwtResponse tokenJwt = authService.authentication(login);
        return ResponseEntity.ok(tokenJwt);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponse> refreshToken( @Valid @RequestBody TokenRefreshRequest refreshRequest) {
       TokenRefreshResponse responseToken = authService.refreshToken(refreshRequest);

       return ResponseEntity.ok(responseToken);

    }

}