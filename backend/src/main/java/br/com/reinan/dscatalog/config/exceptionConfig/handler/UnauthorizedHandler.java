package br.com.reinan.dscatalog.config.exceptionConfig.handler;

import br.com.reinan.dscatalog.controllers.exceptions.StandardError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class UnauthorizedHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        objectMapper.registerModule(new JavaTimeModule());
        if (!response.isCommitted()) {

            StandardError err = new StandardError();
            String uri = request.getRequestURI();

            err.setStatus(HttpStatus.UNAUTHORIZED.value());
            err.setError("Unauthorized");
            err.setMessage("In order to access this recource, you have to be logged in the system");
            err.setPath(uri);

          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          response.getWriter().write(objectMapper.writeValueAsString(err));

        }

    }
}
