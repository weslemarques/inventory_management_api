package br.com.reinan.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.reinan.dscatalog.controllers.exceptions.FieldMessage;
import br.com.reinan.dscatalog.dto.UserInsertDTO;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.UserRepository;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidator implements jakarta.validation.ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User entity = repository.findByEmail(dto.getEmail());

        if (entity != null) {
            list.add(new FieldMessage("email", "Email ja Existe"));
        }
        // Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à
        // lista

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}