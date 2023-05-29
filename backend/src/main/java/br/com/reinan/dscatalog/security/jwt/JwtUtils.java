package br.com.reinan.dscatalog.security.jwt;


import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.services.exceptions.TokenExpiredException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${reinan.jwt.secret}")
    private String jwtSecret;

    @Value("${reinan.jwt.accessTokenExpirationMs}")
    private int tokenExpiration;

    public String generateJwtToken(User userPrincial) {
        return JWT.create().withIssuer("com.reinan")
                .withSubject(userPrincial.getEmail())
                .withClaim("id", userPrincial.getId())
                .withClaim("roles", userPrincial.getAuthorities().stream().map(r -> r.getAuthority()).toList())
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
    