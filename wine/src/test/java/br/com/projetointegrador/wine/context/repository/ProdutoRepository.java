package br.com.projetointegrador.wine.context.repository;

import br.com.projetointegrador.wine.context.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository <Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
}
