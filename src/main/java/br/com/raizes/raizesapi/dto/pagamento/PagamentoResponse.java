package br.com.raizes.raizesapi.dto.pagamento;

import br.com.raizes.raizesapi.enums.FormaPagamento;
import br.com.raizes.raizesapi.enums.StatusPagamento;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponse(
        Long id,
        Long pedidoId,
        FormaPagamento formaPagamento,
        BigDecimal valor,
        StatusPagamento status,
        LocalDateTime dataPagamento
) { }
