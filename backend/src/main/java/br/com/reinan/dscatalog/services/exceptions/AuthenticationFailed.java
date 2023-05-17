package br.com.reinan.dscatalog.services.exceptions;

import java.io.Serial;

public class AuthenticationFailed extends RuntimeException{


    @Serial
    private static final long serialVersionUID = 1L;
    public AuthenticationFailed(String message) {
        super(message);
    }
}
