package br.com.raizes.raizesapi.dto.unidade;

public record UnidadeResponse(
        Long id,
        String nome,
        String endereco,
        Boolean ativa
) {}
