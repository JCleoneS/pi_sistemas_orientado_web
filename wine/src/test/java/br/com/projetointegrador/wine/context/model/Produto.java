package br.com.projetointegrador.wine.context.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria tipo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private double avaliacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grupo grupo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens = new ArrayList<>();


    public Produto(){ }

    public Long getCodigo() {
        return codigo;
    }

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

    public List<Imagem> getImagens() {return imagens;}

    public void setImagens(List<Imagem> imagens) {this.imagens = imagens;}
}
