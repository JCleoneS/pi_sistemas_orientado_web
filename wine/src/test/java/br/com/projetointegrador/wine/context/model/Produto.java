package br.com.projetointegrador.wine.context.model;
import jakarta.persistence.*;

@Entity
public class Produto {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProdCat tipo;

    @Column(nullable = false)
    private String desc;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private int qtde;


    public Produto(){ }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ProdCat getTipo() {
        return tipo;
    }

    public void setTipo(ProdCat tipo) {
        this.tipo = tipo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }
}
