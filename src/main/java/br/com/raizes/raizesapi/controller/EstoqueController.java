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
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
@Tag(name = "Estoque", description = "Endpoints para controle físico e volumétrico de mercadorias por unidade")
public class EstoqueController {

    private final EstoqueService service;

    // GET /estoques -> Lista o saldo atualizado e os produtos registrados no estoque local
    @GetMapping
    @Operation(summary = "Listar estoque geral", description = "Exibe o saldo volumétrico atual de todos os produtos que possuem estoque local ativo")
    public ResponseEntity<List<EstoqueResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /estoques/{id} -> Busca os detalhes de quantidade disponível de um item de estoque pelo ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar estoque por ID", description = "Recupera a quantidade física exata disponível para vendas de um item do estoque específico")
    public ResponseEntity<EstoqueResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // POST /estoques -> Cria o registro inicial de estoque atrelando uma quantidade a um produto específico
    @PostMapping
    @Operation(summary = "Cadastrar estoque inicial", description = "Vincula um saldo de quantidade de estoque inicial para um produto cadastrado")
    public ResponseEntity<EstoqueResponse> cadastrar(@Valid @RequestBody EstoqueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(request));
    }

    // PUT /estoques/{id} -> Atualiza manualmente o saldo da quantidade disponível de um produto em estoque
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estoque manualmente", description = "Ajusta diretamente o saldo numérico da quantidade física disponível em estoque de um produto")
    public ResponseEntity<EstoqueResponse> atualizar(@PathVariable Long id, @Valid @RequestBody EstoqueRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    // DELETE /estoques/{id} -> Remove o registro de estoque do produto do banco de dados
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar registro de estoque", description = "Remove o vínculo físico e histórico de estoque de um determinado produto do sistema")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
