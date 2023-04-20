package br.com.projetointegrador.wine.context.controller;
import br.com.projetointegrador.wine.context.dto.RequisicaoLoginDTO;
import br.com.projetointegrador.wine.context.dto.RequisicaoNovoUsuarioDTO;
import br.com.projetointegrador.wine.context.model.Grupo;
import br.com.projetointegrador.wine.context.repository.UsuarioRepository;

import br.com.projetointegrador.wine.context.model.Usuario;
import br.com.projetointegrador.wine.context.utils.CriptografiaUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping("/loja")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("admin/loja");
        return mv;
    }
    @GetMapping("/")
    public ModelAndView adminHome() {
        ModelAndView mv = new ModelAndView("admin/home");
        return mv;
    }
}
