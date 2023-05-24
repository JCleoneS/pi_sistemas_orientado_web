package br.com.projetointegrador.wine.context.controller;
import br.com.projetointegrador.wine.context.dto.RequisicaoEditarProdutoDTO;
import br.com.projetointegrador.wine.context.dto.RequisicaoNovoProdutoDTO;
import br.com.projetointegrador.wine.context.model.*;
import br.com.projetointegrador.wine.context.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


@Controller
@RequestMapping(value = "/produtos")
public class ProdutoController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/admin/images";

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("")
    public ModelAndView prdIndex(@RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 100, Sort.by("codigo").descending());
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        ModelAndView mv = new ModelAndView("admin/indexProduto");
        mv.addObject("produtos", produtos.getContent());
        mv.addObject("paginaAtual", pagina);
        mv.addObject("totalPaginas", produtos.getTotalPages());
        return mv;
    }

    @GetMapping("/catalogo")
    public ModelAndView prdCatalogo(@RequestParam(defaultValue = "0") int pagina){
        Pageable pageable = PageRequest.of(pagina, 10);
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        ModelAndView mv = new ModelAndView("admin/catalogo");
        mv.addObject("produtos", produtos.getContent());
        mv.addObject("paginaAtual", pagina);
        mv.addObject("totalPaginas", produtos.getTotalPages());
        return mv;
    }

    @GetMapping("/paginado")
    public ModelAndView prdPaginado(@RequestParam(defaultValue = "0") int pagina){
        Pageable pageable = PageRequest.of(pagina, 10);
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        ModelAndView mv = new ModelAndView("admin/paginado");
        mv.addObject("produtos", produtos.getContent());
        mv.addObject("paginaAtual", pagina);
        mv.addObject("totalPaginas", produtos.getTotalPages());
        return mv;
    }


    @GetMapping("/novo-produto")
    public ModelAndView newProduto(RequisicaoNovoProdutoDTO requisicaoNovoProdutoDTO) {
        ModelAndView mv = new ModelAndView("admin/novo-produto");
        mv.addObject("categorias", Categoria.values());
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoNovoProdutoDTO requisicao, BindingResult result, @RequestParam("imagens") List<MultipartFile> arquivos) throws IOException {
        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("admin/novo-produto");
            mv.addObject("categorias", Categoria.values());
            return mv;
        }
        Produto produto = requisicao.toProduto();
        produto.setSituacao(Situacao.ATIVO);
//        String imageUUID;
//        for (MultipartFile arquivo : arquivos) {
//            if (!arquivos.isEmpty()) {
//                imageUUID = arquivo.getOriginalFilename();
//                Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
//                Files.write(fileNameAndPath, arquivo.getBytes());
//            }else{
//                imageUUID = imgName;
//            }
//            ImagemProduto.setBase64(imageUUID);
//            produtoRepository.save(produto);
//        }
        for (MultipartFile arquivo : arquivos) {
            ImagemProduto imagemProduto = new ImagemProduto();
            byte[] bytes = arquivo.getBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            imagemProduto.setBase64(base64);
            imagemProduto.setProduto(produto);
            produto.getImagens().add(imagemProduto);
        }
        produtoRepository.save(produto);
        return new ModelAndView("redirect:/produtos");
    }

    @GetMapping("/buscar")
    public ModelAndView buscarProdutoPorNome(@RequestParam("nome") String nome) {
        List<Produto> produtos = produtoRepository.findAll();
        if(nome != null && !nome.equals("")) {
            produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        }
        ModelAndView mv = new ModelAndView("admin/indexProduto");
        mv.addObject("produtos", produtos);
        return mv;
    }
    @GetMapping("/buscarCatalogo")
    public ModelAndView buscarProdutoPorNome1(@RequestParam("nome") String nome) {
        List<Produto> produtos = produtoRepository.findAll();
        if(nome != null && !nome.equals("")) {
            produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        }
        ModelAndView mv = new ModelAndView("admin/catalogo");
        mv.addObject("produtos", produtos);
        return mv;
    }

    @GetMapping("/{codigo}/inativar")
    public ModelAndView inativar(@PathVariable Long codigo) {
        Optional<Produto> optional = produtoRepository.findById(codigo);
        if (optional.isPresent()) {
            Produto produto = optional.get();
            produto.setSituacao(Situacao.INATIVO);
            produtoRepository.save(produto);
        }
        ModelAndView mv = new ModelAndView("admin/indexProduto");
        mv.addObject("produtos", produtoRepository.findAll());
        return mv;
    }

    @GetMapping("/{codigo}/ativar")
    public ModelAndView ativar(Produto requisicao) {
        Optional<Produto> optional = produtoRepository.findById(requisicao.getCodigo());
        if (optional.isPresent()) {
            Produto produto = optional.get();
            produto.setSituacao(Situacao.ATIVO);
            produtoRepository.save(produto);
        }
        ModelAndView mv = new ModelAndView("admin/indexProduto");
        mv.addObject("produtos", produtoRepository.findAll());
        return mv;
    }

    @GetMapping("/{codigo}/edit")
    public ModelAndView edit(@PathVariable Long codigo, RequisicaoNovoProdutoDTO requisicaoNovoProdutoDTO){
        Optional<Produto> optional = this.produtoRepository.findById(codigo);

        if (optional.isPresent()){
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
            ModelAndView mv = new ModelAndView("admin/edit-produto");
            mv.addObject("produto", requisicaoEditarProdutoDTO);
            mv.addObject("categorias", Categoria.values());
            mv.addObject("situacoes", Situacao.values());

            return mv;
        }
        //não achou um registro na tabela usuario com o id informado
        else{
            System.out.println("Nao achou o produto de codigo: "+codigo);
            return new ModelAndView("redirect:/produtos");
        }
    }

    @PostMapping("/edit")
    public ModelAndView editar(@Valid RequisicaoEditarProdutoDTO requisicao,BindingResult result, @RequestParam("imagens") List<MultipartFile> arquivos) throws IOException {
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
//            produto.setImagens(requisicao.getImagens());
//            for (MultipartFile arquivo : arquivos) {
//                ImagemProduto imagemProduto = new ImagemProduto();
//                byte[] bytes = arquivo.getBytes();
//                String base64 = Base64.getEncoder().encodeToString(bytes);
//                imagemProduto.setBase64(base64);
//                imagemProduto.setProduto(produto);
//                produto.getImagens().add(imagemProduto);
//            }
            produtoRepository.save(produto);
            return new ModelAndView("redirect:/produtos");
        }
        return new ModelAndView("redirect:/produtos");


    }

    @PostMapping("/{codigo}")
    public ModelAndView update(@PathVariable Long codigo,@Valid RequisicaoNovoProdutoDTO requisicao, BindingResult result){
        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("admin/edit-produto");
            mv.addObject("categorias", Categoria.values());
            return mv;
        }else{
            Optional<Produto> optional = this.produtoRepository.findById(codigo);

            if (optional.isPresent()) {
                Produto produto = requisicao.toProduto(optional.get());
                this.produtoRepository.save(produto);

                return new ModelAndView("redirect:/produtos");
                //não achou um registro na tabela usuario com o id informado
            }else{
                System.out.println("Nao achou o produto de codigo: "+codigo);
                return new ModelAndView("redirect:/produtos");

            }
        }
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
            System.out.println("Nao achou o produto de codigo: " + codigo);
            return new ModelAndView("redirect:/produtos");
        }
    }
}
