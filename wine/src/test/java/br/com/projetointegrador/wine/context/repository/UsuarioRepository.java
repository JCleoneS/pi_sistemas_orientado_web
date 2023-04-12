package br.com.projetointegrador.wine.context.repository;

import br.com.projetointegrador.wine.context.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    List<Usuario> findByEmail(String email);
    List<Usuario> findByCpf(String cpf);
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

}