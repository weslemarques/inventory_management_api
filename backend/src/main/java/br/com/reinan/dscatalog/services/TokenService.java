package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    Long expiration;

    public String generateToken(User user) {

        return JWT.create()
                .withIssuer("Produtos")
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(expiration)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256(secret));
    }

    public String generateRefreshToken(User user) {

        return JWT.create()
                .withIssuer("Produtos")
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(30)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256(secret));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("Produtos")
                .build().verify(token).getSubject();

    }
}


