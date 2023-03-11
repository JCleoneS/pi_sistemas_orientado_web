package br.com.projetointegrador.wine.context.model;
import jakarta.persistence.*;

public class LoginUsuarioDTO {

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;

    public LoginUsuarioDTO() { }

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
