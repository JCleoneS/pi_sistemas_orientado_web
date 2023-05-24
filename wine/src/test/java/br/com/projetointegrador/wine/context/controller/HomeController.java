package br.com.projetointegrador.wine.context.controller;
import br.com.projetointegrador.wine.context.controller.ProdutoController;
import br.com.projetointegrador.wine.context.controller.UsuarioController;
import br.com.projetointegrador.wine.context.dto.RequisicaoEditarProdutoDTO;
import br.com.projetointegrador.wine.context.dto.RequisicaoLoginDTO;
import br.com.projetointegrador.wine.context.dto.RequisicaoNovoUsuarioDTO;
import br.com.projetointegrador.wine.context.model.*;
import br.com.projetointegrador.wine.context.repository.UsuarioRepository;
import br.com.projetointegrador.wine.context.repository.ProdutoRepository;
import br.com.projetointegrador.wine.context.utils.CriptografiaUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    ProdutoController produtoController;
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

//    @GetMapping("/carrinho")
//    public ModelAndView carrinho(@Valid RequisicaoEditarProdutoDTO requisicao, BindingResult result, @RequestParam("imagens") List<MultipartFile> arquivos) {
//        List<Produto> produtos = produtoRepository.findAll();
//        ModelAndView mv = new ModelAndView("admin/carrinho");
//        mv.addObject("produtos", produtos.getContent());
//        mv.addObject("categorias", Categoria.values());
//        mv.addObject("situacoes", Situacao.values());
//        return mv;
//
//    }
//
//    @GetMapping("/checkout")
//    public ModelAndView adminHome() {
//        ModelAndView mv = new ModelAndView("admin/checkout");
//        return mv;
//
//    }
}
