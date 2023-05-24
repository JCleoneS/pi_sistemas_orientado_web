package br.com.projetointegrador.wine.context.controller;

import br.com.projetointegrador.wine.context.model.Produto;
import br.com.projetointegrador.wine.context.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/catalogo")
    public ModelAndView getCatalogo(@RequestParam(defaultValue = "0") int pagina){
        Pageable pageable = PageRequest.of(pagina, 10);
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        ModelAndView mv = new ModelAndView("catalogo");
        mv.addObject("produtos", produtos.getContent());
        mv.addObject("paginaAtual", pagina);
        mv.addObject("totalPaginas", produtos.getTotalPages());
        return mv;
    }
}