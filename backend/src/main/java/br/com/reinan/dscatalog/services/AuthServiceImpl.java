package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.request.TokenRefreshRequest;
import br.com.reinan.dscatalog.dto.request.UserLoginDTO;
import br.com.reinan.dscatalog.dto.response.JwtResponse;
import br.com.reinan.dscatalog.dto.response.TokenRefreshResponse;
import br.com.reinan.dscatalog.entities.RefreshToken;
import br.com.reinan.dscatalog.entities.Role;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.security.jwt.JwtUtils;
import br.com.reinan.dscatalog.services.contract.AuthService;
import br.com.reinan.dscatalog.services.contract.RefreshTokenService;
import br.com.reinan.dscatalog.services.exceptions.ResorceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    //    @Override
    public JwtResponse authentication(UserLoginDTO login) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        Authentication authenticate = this.authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        var user = (User) authenticate.getPrincipal();
        List<String> roles = user.getAuthorities().stream().map(Role::getAuthority).toList();

        String token = jwtUtils.generateJwtToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();

        return new JwtResponse(user, token, refreshToken, roles);

    }
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        User user =
                refreshTokenService.findByToken(refreshToken)
                        .map(refreshTokenService::verifyExpiration)
                        .map(RefreshToken::getUser).orElseThrow(() -> new ResorceNotFoundException(""));
        String token = jwtUtils.generateJwtToken(user);

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
        return new TokenRefreshResponse(token, newRefreshToken.getToken());

    }
}