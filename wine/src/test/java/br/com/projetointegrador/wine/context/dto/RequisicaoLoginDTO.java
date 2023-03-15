package br.com.projetointegrador.wine.context.dto;
import br.com.projetointegrador.wine.context.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequisicaoLoginDTO {
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank
    @Column(nullable = false)
    private String senha;

    public RequisicaoLoginDTO() { }

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

    public Usuario toUsuario(){
        Usuario usuario = new Usuario();
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        return usuario;
    }
}
