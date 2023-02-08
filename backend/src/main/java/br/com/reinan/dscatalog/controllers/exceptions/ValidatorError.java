package br.com.reinan.dscatalog.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidatorError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String field, String message) {
        errors.add(new FieldMessage(field, message));
    }

}
