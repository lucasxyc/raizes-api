package br.com.raizes.raizesapi.dto.produto;

import java.math.BigDecimal;

public record ProdutoRequest(

        String nome,
        String descricao,
        BigDecimal preco,
        Boolean ativo

) {
}
