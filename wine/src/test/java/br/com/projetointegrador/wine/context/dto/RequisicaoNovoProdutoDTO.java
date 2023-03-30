package br.com.projetointegrador.wine.context.dto;
import jakarta.persistence.*;
import br.com.projetointegrador.wine.context.model.Categoria;
import br.com.projetointegrador.wine.context.model.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class RequisicaoNovoProdutoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cod;
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
    private String descri;
    @NotNull

    @Column(nullable = false)
    private double preco;
    @NotNull
    @Column(nullable = false)
    private int qtde;


    public RequisicaoNovoProdutoDTO(){ }

    public Long getCod() {return cod;}

    public void setCod(Long cod) {this.cod = cod;}

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

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
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

    public Produto toProduto(){
        Produto produto = new Produto();
        produto.setNome(this.nome);
        produto.setTipo(this.tipo);
        produto.setDescri(this.descri);
        produto.setPreco(this.preco);
        produto.setQtde(this.qtde);
        return produto;
    }

    public Produto toProduto(Produto produto){
        produto.setNome(this.nome);
        produto.setTipo(this.tipo);
        produto.setDescri(this.descri);
        produto.setPreco(this.preco);
        produto.setQtde(this.qtde);
        return produto;
    }

    public void fromProduto(Produto produto){
        this.nome = produto.getNome();
        this.tipo = produto.getTipo();
        this.descri = produto.getDescri();
        this.preco = produto.getPreco();
        this.qtde = produto.getQtde();
    }
}
