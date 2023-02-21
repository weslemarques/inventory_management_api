package br.com.reinan.dscatalog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.reinan.dscatalog.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
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

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception { // Autentica o usuário.
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
				.passwordEncoder(passwordEncoder);
	}

	@Bean
    public CorsConfigurationSource corsConfigurationSource() {           // Método relacionado á CORS, integração com um meio externo.
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}