package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.security.TokenRefreshResponseDTO;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.UserRepository;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class TokenService {

    static final  String issuer = "wesle.com";
    @Value("${security.jwt.secret}")
    private String acessTokenSecret;

    @Value("${security.jwt.refreshToken.secret}")
    private String refreshTokenSecret;
    @Value("${security.jwt.expiration}")
    private Long expiration;
    @Value("${security.jwt.refreshToken.expiration}")
    private  Long refreshTokenExpiration;

    private final JWTVerifier accessTokenVerifier;
    private final JWTVerifier refreshTokenVerifier;


    public TokenService() {
        Algorithm accessTokenAlgorithm = Algorithm.HMAC256(acessTokenSecret);
        accessTokenVerifier = JWT.require(accessTokenAlgorithm)
                .withIssuer(issuer)
                .build();
        Algorithm refreshTokenAlgorithm = Algorithm.HMAC256(refreshTokenSecret);
        refreshTokenVerifier = JWT.require(refreshTokenAlgorithm)
                .withIssuer(issuer)
                .build();
    }

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
                ).sign(Algorithm.HMAC256(acessTokenSecret));
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
                        .plusMinutes(refreshTokenExpiration)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256(refreshTokenSecret));
    }


    public TokenRefreshResponseDTO refreshToken(String refreshToken) {
        if (refreshToken.contains("Bearer ")) refreshToken =
                refreshToken.substring("Bearer ".length());

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(refreshTokenSecret)).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        Long idUser = decodedJWT.getClaim("id").asLong();
        User user = userRepository.findById(idUser).orElseThrow(() -> new ResorceNotFoundException("Entidade Não Encontrada"));
        return generateToken(user);
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(acessTokenSecret))
                .withIssuer("Produtos")
                .build().verify(token).getSubject();
    }

    public String getSubjectRefresh(String token) {
        return JWT.require(Algorithm.HMAC256(refreshTokenSecret))
                .withIssuer("Produtos")
                .build().verify(token).getSubject();
    }
    private Optional<DecodedJWT> decodeAccessToken(String token) {
        try {
            return Optional.of(accessTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
            //.error("invalid access token", e);
        }
        return Optional.empty();
    }

    private Optional<DecodedJWT> decodeRefreshToken(String token) {
        try {
            return Optional.of(refreshTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
           // log.error("invalid refresh token", e);
        }
        return Optional.empty();
    }
}


