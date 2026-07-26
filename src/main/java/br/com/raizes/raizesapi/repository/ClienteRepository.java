package br.com.raizes.raizesapi.repository;

import br.com.raizes.raizesapi.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository <Cliente, Long> {

}
