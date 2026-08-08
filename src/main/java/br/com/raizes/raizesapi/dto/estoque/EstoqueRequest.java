package br.com.raizes.raizesapi.dto.estoque;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record EstoqueRequest(
        @NotNull(message = "O ID do produto é obrigatório.")
        Long produtoId,

        @NotNull(message = "O ID da unidade é obrigatório.")
        Long unidadeId,

        @NotNull(message = "A quantidade é obrigatória.")
        @PositiveOrZero(message = "A quantidade deve ser maior ou igual a zero.")
        Integer quantidade
) { }
