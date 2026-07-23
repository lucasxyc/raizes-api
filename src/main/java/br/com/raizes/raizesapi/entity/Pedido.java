package br.com.raizes.raizesapi.entity;

import br.com.raizes.raizesapi.enums.CanalPedido;
import br.com.raizes.raizesapi.enums.StatusPedido;
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

public class Pedido {

    private Long id;
    private Cliente cliente;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private CanalPedido canalPedido;
    private BigDecimal valorTotal;

}
