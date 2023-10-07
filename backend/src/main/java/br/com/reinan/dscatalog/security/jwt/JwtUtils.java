package br.com.reinan.dscatalog.security.jwt;


import br.com.reinan.dscatalog.entities.Role;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.services.exceptions.TokenExpiredException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.accessTokenExpirationMs}")
    private int tokenExpiration;

    public String generateJwtToken(User userPrincipal) {
        return JWT.create().withIssuer("com.dscatalog")
                .withSubject(userPrincipal.getEmail())
                .withClaim("id", userPrincipal.getId())
                .withClaim("roles", userPrincipal.getAuthorities().stream().map(Role::getAuthority).toList())
                .withExpiresAt(new Date(new Date().getTime() + tokenExpiration))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String getUsernameFromJwtToken(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        if (isExpired(authToken)) {
            throw new TokenExpiredException("Token Expirado");
        }
        return true;
    }

    public boolean isExpired(String jwtToken) {
            return JWT.decode(jwtToken).getExpiresAt().before(new Date());
    }

}
    