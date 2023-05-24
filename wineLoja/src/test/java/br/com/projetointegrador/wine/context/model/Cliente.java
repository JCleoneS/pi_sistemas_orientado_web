package br.com.projetointegrador.wine.context.model;//package br.com.projetointegrador.wine.context.model;
//
//import jakarta.persistence.*;
//
//public class Cliente {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(nullable = false)
//    private String nome;
//    @Column(nullable = false, unique = true)
//    private String cpf;
//    @Column(nullable = false, unique = true)
//    private String email;
//    @Column(nullable = false)
//    private String senha;
//    private String telefone;
//    @ManyToOne
//    @JoinColumn(name="endereco_id;")
//    @Column(nullable = false)
//    private Endereco endereco;
//    private Endereco endereco_2;
//    @Column(nullable = false)
//    private String cidade;
//    @Column(nullable = false)
//    private String estado;
//    @Column(nullable = false)
//    private Long pais_id;
//}
