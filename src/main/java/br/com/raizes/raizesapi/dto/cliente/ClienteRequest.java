package br.com.raizes.raizesapi.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRequest(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail deve ser válido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        String telefone,

        @NotNull(message = "O consentimento da LGPD é obrigatório.")
        Boolean consentimentoLGPD
) { }
