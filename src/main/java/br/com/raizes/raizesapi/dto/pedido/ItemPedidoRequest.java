package br.com.raizes.raizesapi.dto.pedido;

public record ItemPedidoRequest(
        Long produtoId,
        Integer quantidade
) { }
