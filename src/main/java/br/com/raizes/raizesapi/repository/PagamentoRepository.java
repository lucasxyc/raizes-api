package br.com.raizes.raizesapi.repository;

import br.com.raizes.raizesapi.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
