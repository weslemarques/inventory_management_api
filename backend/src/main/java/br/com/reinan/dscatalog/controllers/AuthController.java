package br.com.reinan.dscatalog.controllers;

import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.security.TokenRefreshRequestDTO;
import br.com.reinan.dscatalog.dto.security.TokenRefreshResponseDTO;
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
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<TokenRefreshResponseDTO> authentication(  @Valid @RequestBody UserLoginDTO login) {

        TokenRefreshResponseDTO tokenJwt = authService.authentication(login);
        return ResponseEntity.ok(tokenJwt);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenRefreshResponseDTO> refreshToken(@RequestBody TokenRefreshRequestDTO refreshToken) {

        String  tokenRefresh = refreshToken.getRefreshToken();
        return ResponseEntity.ok();
    }
}
