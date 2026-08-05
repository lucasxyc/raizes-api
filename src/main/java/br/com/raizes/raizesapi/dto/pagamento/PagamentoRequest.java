package br.com.raizes.raizesapi.dto.pagamento;

public record PagamentoRequest(
        Long pedidoId,
        Boolean aprovado
) { }
