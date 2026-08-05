package br.com.raizes.raizesapi.dto.pagamento;

import jakarta.validation.constraints.NotNull;

public record PagamentoRequest(
        @NotNull(message = "O ID do pedido é obrigatório.")
        Long pedidoId,

        @NotNull(message = "O status de aprovação é obrigatório.")
        Boolean aprovado
) { }
