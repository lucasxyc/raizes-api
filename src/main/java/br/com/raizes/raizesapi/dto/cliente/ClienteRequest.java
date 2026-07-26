package br.com.raizes.raizesapi.dto.cliente;

import lombok.Data;

@Data
public class ClienteRequest {

    private String nome;
    private String email;
    private String telefone;
    private Boolean consentimentoLGPD;

}