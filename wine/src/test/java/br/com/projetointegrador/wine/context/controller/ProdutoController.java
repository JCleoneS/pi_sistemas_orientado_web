package br.com.projetointegrador.wine.context.controller;
import br.com.projetointegrador.wine.context.dto.RequisicaoNovoProdutoDTO;
import br.com.projetointegrador.wine.context.model.*;
import br.com.projetointegrador.wine.context.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
//    Eu quero, eu posso: Listar os produtos da loja
//    Para que: Possível incluir, alterar, visualizar e habilitar/inabilitar o produto
//    Critérios de aceite:
//    Após o login do administrador, a tela principal do backoffice terá o botão de Listar Produtos.
//    Ao clicar no botão haverá a abertura da tela de produtos e ela, por default, deve fazer a procura de todos os produtos e listar os últimos (decrescente) produtos inseridos na base.
//    Um campo de busca de produto, com busca parcial - Ex. smart (vai trazer tudo que conter smart no nome do produto.
//    A lista deve apresentar o código do produto, o nome do produto, a quantidade em estoque, o valor e o status (ativo ou desativado)
//    Terá um botão para chamar a tela de cadastro de produto (representado por um sinal de +)
//    Listará no máximo 10 produtos na página e criará uma barra de paginação.
//    Para cada produto um ícone/link com a ação permitida para o produto (alterar, inativar, reativar, visualizar)
    @GetMapping("")
    public ModelAndView prdIndex(){
        List<Produto> produtos = produtoRepository.findAll();
        ModelAndView mv = new ModelAndView("admin/indexProduto");
        mv.addObject("produtos", produtos);
        return mv;
    }

//    Eu quero, eu posso: Cadastrar um novo produto e suas imagem
//    Para que: Possa disponibilizar o produto na loja
//    Critérios de aceite:
//    Incluir dados de nome de produto (max 200 caracteres), avaliação (de 1 a 5 variando de 0,5 em 0,5), Descrição Detalhadas (2000 caracteres), preço produto (valor monetário) 2 casas decimais, qtd estoque (valor inteiro)
//    Incluir e associar multiplas imagens ao mesmo produto (não limitado).
//    Nas imagens, uma delas tem que ser definida como padrão.
//    A imagem tem que ser carregada antes no diretório do projeto (pelo botão procurar).
//    Ao carregar a imagem o sistema deve trocar o nome e armazenar o caminho com o novo nome no banco de dados.
//    Botão salvar, salva o produto e as referencias das imagens no banco de dados e volta para a tela de lista de produto
//    Botão cancelar, volta para a tela de lista de  produto
//    Os dados  tem que ser refletidas no banco de dados


    @GetMapping("/novo-produto")
    public ModelAndView newProduto(RequisicaoNovoProdutoDTO requisicaoNovoProdutoDTO) {
        ModelAndView mv = new ModelAndView("admin/novo-produto");
        mv.addObject("categorias", Categoria.values());
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoNovoProdutoDTO requisicao, BindingResult result){
        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("admin/novo-produto");
            mv.addObject("categorias", Categoria.values());
            return mv;
        }
        Produto produto = requisicao.toProduto();
        this.produtoRepository.save(produto);
        return new ModelAndView("redirect:/produtos");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long cod, RequisicaoNovoProdutoDTO requisicaoNovoProdutoDTO){
        Optional<Produto> optional = this.produtoRepository.findById(cod);

        if (optional.isPresent()){
            Produto produto = optional.get();
            ModelAndView mv = new ModelAndView("/produtos/edit");
            mv.addObject("produtoCod", produto.getCod());
            mv.addObject("categorias", Categoria.values());
        }
        //não achou um registro na tabela usuario com o id informado
        else{
            System.out.println("Nao achou o produto de codigo: "+cod);
            return new ModelAndView("redirect:/produtos");
        }

        ModelAndView mv = new ModelAndView("/produtos/edit");
        return mv;
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long cod,@Valid RequisicaoNovoProdutoDTO requisicao, BindingResult result){
        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("/produtos/edit");
            mv.addObject("categorias", Categoria.values());
            return mv;
        }else{
            Optional<Produto> optional = this.produtoRepository.findById(cod);

            if (optional.isPresent()) {
                Produto produto = requisicao.toProduto(optional.get());
                this.produtoRepository.save(produto);

                return new ModelAndView("redirect:/produtos" + produto.getCod());
                //não achou um registro na tabela usuario com o id informado
            }else{
                System.out.println("Nao achou o produto de codigo: "+cod);
                return new ModelAndView("redirect:/produtos");

            }
        }
    }
}
