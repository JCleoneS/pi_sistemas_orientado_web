package br.com.projetointegrador.wine.context.dto;
import br.com.projetointegrador.wine.context.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class RequisicaoNovoProdutoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria tipo;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String descricao;
    @NotNull

    @Column(nullable = false)
    private double preco;
    @NotNull
    @Column(nullable = false)
    private int quantidade;

    @NotNull
    @Column(nullable = false)
    private double avaliacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grupo grupo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Situacao situacao;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String imageName;

    public RequisicaoNovoProdutoDTO(){ }

    public Long getCodigo() {return codigo;}

    public void setCodigo(Long codigo) {this.codigo = codigo;}

    public String getNome() {return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getTipo() {
        return tipo;
    }

    public void setTipo(Categoria tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getAvaliacao() {return avaliacao;}

    public void setAvaliacao(double avaliacao) {this.avaliacao = avaliacao;}

    public Grupo getGrupo() {return grupo;}

    public void setGrupo(Grupo grupo) {this.grupo = grupo;}

    public Situacao getSituacao() {return situacao;}

    public void setSituacao(Situacao situacao) {this.situacao = situacao;}
    public String getImageName() {return imageName;}
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    public Produto toProduto(){
        Produto produto = new Produto();
        produto.setNome(this.nome);
        produto.setTipo(this.tipo);
        produto.setDescricao(this.descricao);
        produto.setPreco(this.preco);
        produto.setQuantidade(this.quantidade);
        produto.setAvaliacao(this.avaliacao);
        return produto;
    }

    public Produto toProduto(Produto produto){
        produto.setNome(this.nome);
        produto.setTipo(this.tipo);
        produto.setDescricao(this.descricao);
        produto.setPreco(this.preco);
        produto.setQuantidade(this.quantidade);
        produto.setAvaliacao(this.avaliacao);
        return produto;
    }

    public void fromProduto(Produto produto){
        this.nome = produto.getNome();
        this.tipo = produto.getTipo();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.quantidade = produto.getQuantidade();
        this.avaliacao = produto.getAvaliacao();
    }
}
