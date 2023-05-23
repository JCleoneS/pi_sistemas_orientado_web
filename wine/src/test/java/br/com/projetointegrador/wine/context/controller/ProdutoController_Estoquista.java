package br.com.projetointegrador.wine.context.controller;

import br.com.projetointegrador.wine.context.dto.RequisicaoEditarProdutoDTO;
import br.com.projetointegrador.wine.context.dto.RequisicaoNovoProdutoDTO;
import br.com.projetointegrador.wine.context.model.*;
import br.com.projetointegrador.wine.context.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/produtosEstoquista")
public class ProdutoController_Estoquista {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("")
    public ModelAndView prdIndex(@RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 100, Sort.by("codigo").descending());
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        ModelAndView mv = new ModelAndView("admin/indexProdutoEstoquista");
        mv.addObject("produtos", produtos.getContent());
        mv.addObject("paginaAtual", pagina);
        mv.addObject("totalPaginas", produtos.getTotalPages());
        return mv;
    }

    @GetMapping("/buscar")
    public ModelAndView buscarProdutoPorNome(@RequestParam("nome") String nome) {
        List<Produto> produtos = produtoRepository.findAll();
        if(nome != null && !nome.equals("")) {
            produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        }
        ModelAndView mv = new ModelAndView("admin/indexProdutoEstoquista");
        mv.addObject("produtos", produtos);
        return mv;
    }

    @GetMapping("/{codigo}/editProdutoEstoquista")
    public ModelAndView edit(@PathVariable Long codigo, RequisicaoNovoProdutoDTO requisicaoNovoProdutoDTO){
        Optional<Produto> optional = this.produtoRepository.findById(codigo);

        if (optional.isPresent()) {
            Produto produto = optional.get();
            RequisicaoEditarProdutoDTO requisicaoEditarProdutoDTO = new RequisicaoEditarProdutoDTO();
            requisicaoEditarProdutoDTO.setCodigo(produto.getCodigo());
            requisicaoEditarProdutoDTO.setNome(produto.getNome());
            requisicaoEditarProdutoDTO.setTipo(produto.getTipo());
            requisicaoEditarProdutoDTO.setDescricao(produto.getDescricao());
            requisicaoEditarProdutoDTO.setAvaliacao(produto.getAvaliacao());
            requisicaoEditarProdutoDTO.setPreco(produto.getPreco());
            requisicaoEditarProdutoDTO.setQuantidade(produto.getQuantidade());
            requisicaoEditarProdutoDTO.setSituacao(produto.getSituacao());

            ModelAndView mv = new ModelAndView("admin/editProdutoEstoquista");
            mv.addObject("produto", requisicaoEditarProdutoDTO);
            mv.addObject("categorias", Categoria.values());
            mv.addObject("situacoes", Situacao.values());
            return mv;
        } else {
            System.out.println("Não achou o produto de código: " + codigo);
            return new ModelAndView("redirect:/produtosEstoquista");
        }
    }


    @PostMapping("/editProdutoEstoquista")
    public ModelAndView editar(@Valid RequisicaoEditarProdutoDTO requisicao) {
        Optional<Produto> produtoOptional = produtoRepository.findById(requisicao.getCodigo());
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setCodigo(requisicao.getCodigo());
            produto.setNome(requisicao.getNome());
            produto.setTipo(requisicao.getTipo());
            produto.setDescricao(requisicao.getDescricao());
            produto.setAvaliacao(requisicao.getAvaliacao());
            produto.setPreco(requisicao.getPreco());
            produto.setQuantidade(requisicao.getQuantidade());
            produto.setSituacao(requisicao.getSituacao());
            produtoRepository.save(produto);
            return new ModelAndView("redirect:/produtosEstoquista");
        }
        return new ModelAndView("redirect:/produtosEstoquista");
    }

    @GetMapping("/{codigo}/produto-detail")
    public ModelAndView prdDetail(@PathVariable Long codigo, RequisicaoNovoProdutoDTO requisicaoNovoProdutoDTO) {
        Optional<Produto> optional = this.produtoRepository.findById(codigo);

        if (optional.isPresent()){
            Produto produto = optional.get();
            ModelAndView mv = new ModelAndView("admin/produto-detail");
            mv.addObject("produto", produto);
            mv.addObject("produtoCod", produto.getCodigo());
            mv.addObject("nome", produto.getNome());
            mv.addObject("avaliacao", produto.getAvaliacao());
            mv.addObject("descricao", produto.getDescricao());
            mv.addObject("preco", produto.getPreco());
            mv.addObject("imagens", produto.getImagens());
            return mv;
        }
        //não achou um registro na tabela usuario com o id informado
        else{
            System.out.println("Nao achou o produto de codigo: "+codigo);
            return new ModelAndView("redirect:/produtosEstoquista");
        }
    }
}
