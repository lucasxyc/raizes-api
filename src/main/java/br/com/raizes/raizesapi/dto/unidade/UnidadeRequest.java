package br.com.raizes.raizesapi.dto.unidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UnidadeRequest(
        @NotBlank(message = "O nome da unidade é obrigatório.")
        String nome,

        @NotBlank(message = "O endereço da unidade é obrigatório.")
        String endereco,

        @NotNull(message = "O status ativa da unidade é obrigatório.")
        Boolean ativa
) {}
