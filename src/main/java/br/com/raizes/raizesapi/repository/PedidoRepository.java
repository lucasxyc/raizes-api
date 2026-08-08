package br.com.raizes.raizesapi.repository;

import br.com.raizes.raizesapi.entity.Pedido;
import br.com.raizes.raizesapi.enums.CanalPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByCanalPedido(CanalPedido canalPedido);
}
