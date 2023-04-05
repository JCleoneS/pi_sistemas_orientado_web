package br.com.projetointegrador.wine.context.validacao;

import br.com.projetointegrador.wine.context.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailUnicoValidator implements ConstraintValidator<EmailUnico, String> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean isValid(String email, ConstraintValidatorContext context) {
        return usuarioRepository.findByEmail(email).size() == 0;
    }
}
