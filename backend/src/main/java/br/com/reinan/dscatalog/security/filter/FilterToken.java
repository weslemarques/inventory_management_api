package br.com.reinan.dscatalog.security.filter;

import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.UserRepository;
import br.com.reinan.dscatalog.security.jwt.JwtUtils;
import br.com.reinan.dscatalog.services.exceptions.ResourceNotFoundException;
import br.com.reinan.dscatalog.services.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@Getter
public class FilterToken extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public FilterToken(JwtUtils jwtUtils, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = decodeToken(request);
        if(token != null){
            if(!jwtUtils.validateJwtToken(token))
                throw new TokenExpiredException("Token Expirado");
            User user = recoverUser(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    public User recoverUser(String token){
        String username = jwtUtils.getUsernameFromJwtToken(token);
        return  userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    public  String decodeToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null){
            return token.replace("Bearer ", "");
        }
        return null;
    }


}
