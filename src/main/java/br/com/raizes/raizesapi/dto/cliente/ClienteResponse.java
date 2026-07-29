package br.com.raizes.raizesapi.dto.cliente;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        Integer pontos,
        Boolean consentimentoLGPD
) { }
