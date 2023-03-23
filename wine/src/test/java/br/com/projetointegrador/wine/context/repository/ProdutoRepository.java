package br.com.projetointegrador.wine.context.repository;

import br.com.projetointegrador.wine.context.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface ProdutoRepository extends JpaRepository <Usuario, Long> {
}
