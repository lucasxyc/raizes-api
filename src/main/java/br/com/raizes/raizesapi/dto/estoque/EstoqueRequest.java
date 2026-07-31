package br.com.raizes.raizesapi.dto.estoque;

public record EstoqueRequest(
        Long produtoId,
        Integer quantidade
) { }
