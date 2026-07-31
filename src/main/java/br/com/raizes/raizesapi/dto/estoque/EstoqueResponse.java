package br.com.raizes.raizesapi.dto.estoque;

public record EstoqueResponse(
        Long id,
        Long produtoId,
        String nomeProduto,
        Integer quantidadeDisponivel
) { }
