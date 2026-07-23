package br.com.raizes.raizesapi.entity;

import br.com.raizes.raizesapi.enums.Role;
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

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Role role;

}
