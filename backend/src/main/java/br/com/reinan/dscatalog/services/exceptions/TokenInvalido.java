package br.com.reinan.dscatalog.services.exceptions;

public class TokenInvalido extends RuntimeException {

    public TokenInvalido(String message) {
        super(message);
    }
}
