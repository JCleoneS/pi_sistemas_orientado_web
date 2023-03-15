package br.com.projetointegrador.wine.context.controller;

import br.com.projetointegrador.wine.context.model.Usuario;
import br.com.projetointegrador.wine.context.model.LoginUsuarioDTO;
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
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("areaAdministrativa/login");
        return modelAndView;
    }

    @PostMapping("/acessarAreaAdministrativa")
    public ModelAndView acessarAreaAdministrativa(@Valid LoginUsuarioDTO loginUsuarioDTO, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Tem erros!");
            return new ModelAndView("redirect:/areaAdministrativa/login");
        }
        Usuario usuarioRequisicao = loginUsuarioDTO.toUsuario();
        String email = usuarioRequisicao.getEmail();
        String senha = CriptografiaUtils.criptografar(usuarioRequisicao.getSenha());

        List<Usuario> usuarioBanco = this.usuarioRepository.findAll();
        for (Usuario usuario: usuarioBanco) {
            if (email.equals(usuarioRequisicao.getEmail())){
                String senhaBanco = usuario.getSenha();
                if(senhaBanco.equals(senha)){
                    return new ModelAndView("/areaAdministrativa/home");
                }
            }
        }
        return new ModelAndView("redirect:/loginAdministrativo");
    }


}
