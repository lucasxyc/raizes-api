package br.com.raizes.raizesapi.dto.produto;

import java.math.BigDecimal;

public record ProdutoResponse(

        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean ativo

) {
}
