package br.com.projetointegrador.wine.context.validacao;

import br.com.projetointegrador.wine.context.model.Situacao;
import br.com.projetointegrador.wine.context.model.Usuario;
import br.com.projetointegrador.wine.context.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValidaUsuarioAtivoValidator implements ConstraintValidator<ValidaUsuarioAtivo, String> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean isValid(String email, ConstraintValidatorContext context) {
        List<Usuario> listaUsuario = usuarioRepository.findByEmail(email);
        boolean result = false;
        if(listaUsuario != null && listaUsuario.size() > 0) {
            result = listaUsuario.get(0).getSituacao().equals(Situacao.ATIVO);
        }
        return result;
    }
}
