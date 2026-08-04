package br.com.raizes.raizesapi.dto.pedido;

import br.com.raizes.raizesapi.enums.CanalPedido;
import br.com.raizes.raizesapi.enums.FormaPagamento;
import java.util.List;

public record PedidoRequest(
        Long clienteId,
        FormaPagamento formaPagamento,
        CanalPedido canalPedido,
        List<ItemPedidoRequest> itens
) { }
