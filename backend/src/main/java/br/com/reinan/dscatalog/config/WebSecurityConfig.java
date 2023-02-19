package br.com.reinan.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.reinan.dscatalog.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                    .requestMatchers("/categories/**").hasRole("ROLE_ADMIN")
                    .requestMatchers("/products/**").hasRole("ROLE_OPERATOR")
                    .requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("ROLE_ADMIN", "ROLE_OPERATOR")
                    .requestMatchers(HttpMethod.POST, "/users").hasRole("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/users/**").hasRole("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ROLE_ADMIN")
                    .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
				.requestCache().disable()
                .csrf().disable()
                .build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder);
    }

}