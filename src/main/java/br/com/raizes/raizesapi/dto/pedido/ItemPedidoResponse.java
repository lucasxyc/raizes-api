package br.com.raizes.raizesapi.dto.pedido;

import java.math.BigDecimal;

// Record auxiliar para o retorno dos itens dentro da lista
public record ItemPedidoResponse(
        Long produtoId,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario
) { }