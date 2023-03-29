package br.com.projetointegrador.wine.context.repository;

import br.com.projetointegrador.wine.context.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository <Usuario, Long> {
}
