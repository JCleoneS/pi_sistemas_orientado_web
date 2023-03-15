package br.com.projetointegrador.wine.context.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProdutoController {
    @GetMapping("/produtos")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("admin/produtos");
        return mv;
    }
}
