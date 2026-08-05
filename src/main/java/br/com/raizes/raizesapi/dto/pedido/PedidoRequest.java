package br.com.raizes.raizesapi.dto.pedido;

import br.com.raizes.raizesapi.enums.CanalPedido;
import br.com.raizes.raizesapi.enums.FormaPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PedidoRequest(
        @NotNull(message = "O ID do cliente é obrigatório.")
        Long clienteId,

        @NotNull(message = "A forma de pagamento é obrigatória.")
        FormaPagamento formaPagamento,

        @NotNull(message = "O canal do pedido é obrigatório.")
        CanalPedido canalPedido,

        @NotEmpty(message = "O pedido deve conter pelo menos um item.")
        @Valid
        List<ItemPedidoRequest> itens
) { }
