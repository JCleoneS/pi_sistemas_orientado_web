package br.com.projetointegrador.wine.context.controller;

import br.com.projetointegrador.wine.context.dto.RequisicaoLoginDTO;
import br.com.projetointegrador.wine.context.model.Usuario;
import br.com.projetointegrador.wine.context.repository.UsuarioRepository;
import br.com.projetointegrador.wine.context.utils.CriptografiaUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/loginAdministrativo")
    public ModelAndView login(RequisicaoLoginDTO loginUsuarioDTO) {
        ModelAndView modelAndView = new ModelAndView("admin/login");
        return modelAndView;
    }

    @PostMapping("/acessarAreaAdministrativa")
    public ModelAndView acessarAreaAdministrativa(@Valid RequisicaoLoginDTO usuarioDaRequisicao, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/login");
        }
        String email = usuarioDaRequisicao.getEmail();
        String senhaDaRequisicao = CriptografiaUtils.criptografar(usuarioDaRequisicao.getSenha());
        List<Usuario> usuarioDoBanco = usuarioRepository.findByEmail(email);
        if (usuarioDoBanco.size() > 0) {
            String senhaDoBanco = usuarioDoBanco.get(0).getSenha();
            if (senhaDoBanco.equals(senhaDaRequisicao)) {
                Usuario usuarioAutenticado = usuarioDoBanco.get(0);
                if (usuarioAutenticado.getGrupo().toString().contains("ADMIN")) {
                    return new ModelAndView("/admin/home");
                } else if (usuarioAutenticado.getGrupo().toString().contains("ESTOQUISTA")) {
                    return new ModelAndView("/admin/estoquistaHome");
                }
            }
        }

        return new ModelAndView("redirect:/loginAdministrativo");
    }

}
