package br.com.raizes.raizesapi.dto.cliente;

import lombok.Data;

@Data
public class ClienteResponse {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Integer pontos;
    private Boolean consentimentoLGPD;

}