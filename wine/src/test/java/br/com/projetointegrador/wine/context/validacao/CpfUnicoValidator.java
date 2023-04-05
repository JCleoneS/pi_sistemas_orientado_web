package br.com.projetointegrador.wine.context.validacao;

import br.com.projetointegrador.wine.context.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CpfUnicoValidator implements ConstraintValidator<CpfUnico, String> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return usuarioRepository.findByCpf(cpf).size() == 0;
    }
}
