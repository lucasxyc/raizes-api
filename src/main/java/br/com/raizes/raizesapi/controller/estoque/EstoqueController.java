package br.com.raizes.raizesapi.controller.estoque;

import br.com.raizes.raizesapi.dto.estoque.EstoqueRequest;
import br.com.raizes.raizesapi.dto.estoque.EstoqueResponse;
import br.com.raizes.raizesapi.service.estoque.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService service;

    @GetMapping
    public ResponseEntity<List<EstoqueResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EstoqueResponse> cadastrar(@RequestBody EstoqueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueResponse> atualizar(@PathVariable Long id, @RequestBody EstoqueRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
