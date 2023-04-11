package br.com.projetointegrador.wine.context.validacao;

import br.com.projetointegrador.wine.context.model.Situacao;
import br.com.projetointegrador.wine.context.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidaUsuarioAtivoValidator implements ConstraintValidator<ValidaUsuarioAtivo, String> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean isValid(String email, ConstraintValidatorContext context) {
        return usuarioRepository.findByEmail(email).get(0).getSituacao().equals(Situacao.ATIVO);
    }
}
