package br.com.raizes.raizesapi.dto.cliente;

public record ClienteRequest(
        String nome,
        String email,
        String telefone,
        Boolean consentimentoLGPD
) { }
