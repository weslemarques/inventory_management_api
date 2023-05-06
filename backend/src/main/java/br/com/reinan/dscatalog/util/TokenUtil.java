package br.com.reinan.dscatalog.util;

import br.com.reinan.dscatalog.dto.UserDTO;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Component
public class TokenUtil {

    public String generateToken(UserDTO user) {

        return JWT.create()
                .withIssuer("Produtos")
                .withSubject(user.getEmail())
                .withClaim("id",  user.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(30)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256("secret"));
    }

}
