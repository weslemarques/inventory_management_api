package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.request.TokenRefreshRequest;
import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.response.JwtResponse;
import br.com.reinan.dscatalog.dto.response.TokenRefreshResponse;
import br.com.reinan.dscatalog.security.jwt.JwtUtils;
import br.com.reinan.dscatalog.services.contract.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    final
    JwtUtils jwtUtils;

    private final AuthService authService;

    public AuthController(JwtUtils jwtUtils, AuthService authService) {
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }

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