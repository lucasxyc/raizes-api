package br.com.raizes.raizesapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Integer pontos;
    private Boolean consentimentoLGPD;

}
