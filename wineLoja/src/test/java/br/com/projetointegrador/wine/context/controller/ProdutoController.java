package br.com.projetointegrador.wine.context.controller;

import br.com.projetointegrador.wine.context.model.Produto;
import br.com.projetointegrador.wine.context.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/catalogo")
    public ModelAndView getCatalogo(@RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10);
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        ModelAndView mv = new ModelAndView("catalogo");
        mv.addObject("produtos", produtos.getContent());
        mv.addObject("paginaAtual", pagina);
        mv.addObject("totalPaginas", produtos.getTotalPages());
        return mv;
    }

    @GetMapping("/{codigo}/produto-detail")
    public ModelAndView prdDetail(@PathVariable Long codigo) {
        Optional<Produto> optional = this.produtoRepository.findById(codigo);
        if (optional.isPresent()) {
            Produto produto = optional.get();
            ModelAndView mv = new ModelAndView("produto-detail");
            mv.addObject("produto", produto);
            mv.addObject("produtoCod", produto.getCodigo());
            mv.addObject("nome", produto.getNome());
            mv.addObject("avaliacao", produto.getAvaliacao());
            mv.addObject("descricao", produto.getDescricao());
            mv.addObject("preco", produto.getPreco());
            mv.addObject("imagens", produto.getImagens());
            return mv;
        } else {
            System.out.println("Nao achou o produto de codigo: " + codigo);
            return new ModelAndView("redirect:/produtos");
        }
    }
}