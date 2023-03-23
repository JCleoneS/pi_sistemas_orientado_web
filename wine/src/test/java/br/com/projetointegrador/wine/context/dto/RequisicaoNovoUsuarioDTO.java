package br.com.projetointegrador.wine.context.dto;
import br.com.projetointegrador.wine.context.model.Grupo;
import br.com.projetointegrador.wine.context.model.Situacao;
import br.com.projetointegrador.wine.context.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class RequisicaoNovoUsuarioDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String nome;
    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String cpf;
    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grupo grupo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    public RequisicaoNovoUsuarioDTO() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Usuario toUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setCpf(this.cpf);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        usuario.setSituacao(this.situacao);
        usuario.setGrupo(this.grupo);
        return usuario;
    }

    public Usuario toUsuario(Usuario usuario){
        usuario.setNome(this.nome);
        usuario.setCpf(this.cpf);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        usuario.setSituacao(this.situacao);
        usuario.setGrupo(this.grupo);
        return usuario;
    }

    public void fromUsuario(Usuario usuario){
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.situacao = usuario.getSituacao();
        this.grupo = usuario.getGrupo();
    }
}
