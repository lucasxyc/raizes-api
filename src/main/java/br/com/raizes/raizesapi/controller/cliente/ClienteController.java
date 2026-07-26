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

    // Requisição: GET /clientes
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // Requisição: GET /clientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Requisição: POST /clientes
    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    // Requisição: PUT /clientes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    // Requisição: DELETE /clientes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}