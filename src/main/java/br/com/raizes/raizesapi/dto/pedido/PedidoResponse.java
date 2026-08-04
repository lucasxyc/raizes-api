package br.com.raizes.raizesapi.dto.pedido;

import br.com.raizes.raizesapi.enums.CanalPedido;
import br.com.raizes.raizesapi.enums.FormaPagamento;
import br.com.raizes.raizesapi.enums.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        Long clienteId,
        String nomeCliente,
        StatusPedido status,
        BigDecimal valorTotal,
        FormaPagamento formaPagamento,
        CanalPedido canalPedido,
        List<ItemPedidoResponse> itens
) { }
