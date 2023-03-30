package br.com.projetointegrador.wine.context.repository;

import br.com.projetointegrador.wine.context.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository <Produto, Long> {
}
