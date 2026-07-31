package br.com.raizes.raizesapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estoques")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Estoque {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long Id;

    @OneToOne
    @JoinColumn(name = "produto_id", nullable = false, unique = true)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidadeDisponivel;

}
