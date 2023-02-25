package br.com.reinan.dscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/products**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/products","/users").hasRole("ROLE_USER")
                .requestMatchers(HttpMethod.DELETE, "/products/**","/users").hasRole("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
                .csrf().disable();
        return http.build();
    }
}