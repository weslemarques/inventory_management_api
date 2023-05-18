package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.UserRepository;
import br.com.reinan.dscatalog.services.exceptions.TokenInvalido;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterToken extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public FilterToken(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    protected  void doFilterInternal(HttpServletRequest request,
                                             HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = recoverToken(request);

        if (jwtToken != null) {
            User user = recoverUser(jwtToken);
            var authentication = new UsernamePasswordAuthenticationToken(user,
                    null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);

    }

    private User recoverUser(String tokenJWT) {
        String subject = tokenService.getSubject(tokenJWT);
        return userRepository.findByEmail(subject).orElseThrow(() -> new TokenInvalido("Token Invalido"));
    }

    private String recoverToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null || authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
