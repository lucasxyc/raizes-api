package br.com.raizes.raizesapi.repository;

import br.com.raizes.raizesapi.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository <Estoque, Long> {

    Optional<Estoque> findByProdutoId(Long produtoId);

}
