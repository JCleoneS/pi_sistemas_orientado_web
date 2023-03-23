package br.com.projetointegrador.wine.context.controller;

import br.com.projetointegrador.wine.context.dto.RequisicaoNovoUsuarioDTO;
import br.com.projetointegrador.wine.context.model.*;
import br.com.projetointegrador.wine.context.repository.ProdutoRepository;
import br.com.projetointegrador.wine.context.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProdutoController {
    @GetMapping("/produtos")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("admin/produtos");
        return mv;
    }

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("")
    public ModelAndView prodList(){
        List<Produto> produtos = produtoRepository.findAll();
        ModelAndView mv = new ModelAndView("admin/index");
        mv.addObject("produtos", produtos);
        return mv;
    }

    @GetMapping("/new")
    public ModelAndView newProduto(RequisicaoNovoUsuarioDTO requisicaoNovoUsuarioDTO) {
        ModelAndView mv = new ModelAndView("produto/new");
        mv.addObject("situacoes", ProdCat.values());

        return mv;
    }
}
