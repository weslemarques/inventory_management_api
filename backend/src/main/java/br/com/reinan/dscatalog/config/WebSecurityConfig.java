package br.com.reinan.dscatalog.config;

import br.com.reinan.dscatalog.config.exceptionConfig.handler.UnauthorizedHandler;
import br.com.reinan.dscatalog.security.filter.FilterToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private FilterToken filterToken;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests() // Autorização de requests.
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // Permitindo os recursos do swagger. (Todos podem acessar).
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                .and()
                .csrf().disable().cors().disable()
                .authorizeHttpRequests()
                .requestMatchers("/v1/auth/**", "/v1/refreshtoken").permitAll()
                .requestMatchers(HttpMethod.GET, "/v1/products/**", "/v1/categories/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/v1/products").hasAnyRole("OPERATOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/v1/products/**").hasAnyRole("OPERATOR")
                .requestMatchers("/v1/users", "/v1/users/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(filterToken, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new UnauthorizedHandler());
                // .and().exceptionHandling().accessDeniedHandler();
        return http.build();
    }

}

