package br.com.raizes.raizesapi.dto.auth;

import br.com.raizes.raizesapi.enums.Role;

public record RegisterRequest (String nome, String email, String senha, Role role) {

}
