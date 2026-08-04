package br.com.raizes.raizesapi.controller.cliente;

import br.com.raizes.raizesapi.dto.cliente.ClienteRequest;
import br.com.raizes.raizesapi.dto.cliente.ClienteResponse;
import br.com.raizes.raizesapi.service.cliente.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    // GET /clientes -> Lista todos os clientes cadastrados com suas informações básicas
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /clientes/{id} -> Busca os detalhes e o saldo de pontos de um cliente específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // POST /clientes -> Cadastra um novo cliente coletando o consentimento explícito da LGPD
    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    // PUT /clientes/{id} -> Atualiza os dados de contato ou o consentimento de um cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    // DELETE /clientes/{id} -> Remove permanentemente o registro do cliente do banco de dados
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
