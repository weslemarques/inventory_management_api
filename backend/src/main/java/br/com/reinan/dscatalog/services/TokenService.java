package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.entities.Role;
import br.com.reinan.dscatalog.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Service
public class TokenService {

    public String generateToken(User user, List<String> roles) {

        String token = JWT.create()
                .withIssuer("Produtos")
                .withClaim("roles", roles)
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(30)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256("gferwgqewgqfaqf"));

        return token;
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("gferwgqewgqfaqf"))
                .withIssuer("Produtos")
                .build().verify(token).getSubject();

    }
}


