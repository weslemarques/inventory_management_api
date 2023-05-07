package br.com.reinan.dscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/products/**", "/categories/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**", "/products", "/categories").hasRole("ADMIN")
                .and()
                .csrf().disable();
        return http.build();
    }
}