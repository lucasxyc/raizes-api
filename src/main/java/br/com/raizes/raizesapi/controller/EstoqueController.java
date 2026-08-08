package br.com.raizes.raizesapi.controller;

import br.com.raizes.raizesapi.dto.estoque.EstoqueRequest;
import br.com.raizes.raizesapi.dto.estoque.EstoqueResponse;
import br.com.raizes.raizesapi.service.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
@Tag(name = "Estoque", description = "Endpoints para controle físico e volumétrico de mercadorias por unidade")
public class EstoqueController {

    private final EstoqueService service;

    @GetMapping
    @Operation(summary = "Listar estoque geral", description = "Exibe o saldo volumétrico de todas as unidades de forma paginada")
    public ResponseEntity<org.springframework.data.domain.Page<br.com.raizes.raizesapi.dto.estoque.EstoqueResponse>> listar(
            @org.springframework.data.web.PageableDefault(size = 10, sort = "id") org.springframework.data.domain.Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(pageable));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar estoque por ID", description = "Recupera os detalhes de um item físico específico")
    public ResponseEntity<EstoqueResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PostMapping
    @Operation(summary = "Cadastrar estoque inicial", description = "Vincula um saldo de produto a uma unidade específica")
    public ResponseEntity<EstoqueResponse> cadastrar(@Valid @RequestBody EstoqueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estoque manualmente", description = "Ajusta o saldo da quantidade disponível")
    public ResponseEntity<EstoqueResponse> atualizar(@PathVariable Long id, @Valid @RequestBody EstoqueRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar registro de estoque", description = "Remove o registro de saldo de produto do banco")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
