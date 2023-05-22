package br.com.reinan.dscatalog.services.exceptions;

public class TokenInvalidException extends RuntimeException{

    public TokenInvalidException(String message) {
        super(message);
    }
}
