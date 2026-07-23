package br.com.raizes.raizesapi.entity;

import br.com.raizes.raizesapi.enums.FormaPagamento;
import br.com.raizes.raizesapi.enums.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Pagamento {

    private Long id;
    private Pedido pedido;
    private StatusPagamento status;
    private FormaPagamento forma;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;

}
