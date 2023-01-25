package br.com.reinan.dscatalog.services.exceptions;

public class ResorceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResorceNotFoundException(String msg) {
        super(msg);
    }

}
