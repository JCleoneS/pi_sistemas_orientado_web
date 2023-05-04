package br.com.projetointegrador.wine.context.model;

import jakarta.persistence.*;

@Entity
public class ImagemProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base64", columnDefinition = "BLOB")
    private String base64;

    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}