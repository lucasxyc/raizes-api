package br.com.raizes.raizesapi.controller;

import br.com.raizes.raizesapi.dto.cliente.ClienteRequest;
import br.com.raizes.raizesapi.dto.cliente.ClienteResponse;
import br.com.raizes.raizesapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Endpoints para gerenciamento do cadastro de clientes e pontuação de fidelidade")
public class ClienteController {

    private final ClienteService service;

    // GET /clientes -> Lista todos os clientes cadastrados com suas informações básicas
    @GetMapping
    @Operation(summary = "Listar clientes", description = "Retorna uma lista contendo todos os clientes cadastrados no banco de dados")
    public ResponseEntity<List<ClienteResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /clientes/{id} -> Busca os detalhes e o saldo de pontos de um cliente específico pelo ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Recupera as informações detalhadas e o saldo de pontos de fidelidade acumulados de um único cliente")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // POST /clientes -> Cadastra um novo cliente coletando o consentimento explícito da LGPD
    @PostMapping
    @Operation(summary = "Cadastrar cliente", description = "Insere um novo cliente no sistema validando obrigatoriamente o consentimento explícito da LGPD")
    public ResponseEntity<ClienteResponse> criar(@Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    // PUT /clientes/{id} -> Atualiza os dados de contato ou o consentimento de um cliente existente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Modifica os dados cadastrais ou revoga/valida o consentimento de um cliente existente pelo ID")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    // DELETE /clientes/{id} -> Remove permanentemente o registro do cliente do banco de dados
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente", description = "Exclui definitivamente o registro do cliente do banco de dados relacional")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
