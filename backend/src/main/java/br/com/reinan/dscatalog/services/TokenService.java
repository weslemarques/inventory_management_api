package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.security.TokenRefreshResponseDTO;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.UserRepository;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

    public String createToken(User user) {

        return JWT.create()
                .withIssuer("Produtos")
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(expiration)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256(secret));
    }

    public TokenRefreshResponseDTO generateToken(User user){
       String acessToken  = this.createToken(user);
       String refreshToken  = this.generateRefreshToken(user);
        return  new TokenRefreshResponseDTO(acessToken,refreshToken);
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


    public TokenRefreshResponseDTO refreshToken(String refreshToken) {
        if (refreshToken.contains("Bearer ")) refreshToken =
                refreshToken.substring("Bearer ".length());

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        Long idUser = decodedJWT.getClaim("id").asLong();
        User user = userRepository.findById(idUser).orElseThrow(() -> new ResorceNotFoundException("Entidade Não Encontrada"));
        return generateToken(user);
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("Produtos")
                .build().verify(token).getSubject();

    }
}


