package br.com.raizes.raizesapi.controller;

import br.com.raizes.raizesapi.dto.pedido.PedidoRequest;
import br.com.raizes.raizesapi.dto.pedido.PedidoResponse;
import br.com.raizes.raizesapi.enums.StatusPedido;
import br.com.raizes.raizesapi.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    // POST /pedidos -> Cria um novo pedido com os produtos selecionados
    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody PedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPedido(request));
    }

    // GET /pedidos -> Lista todos os pedidos cadastrados no sistema
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /pedidos/{id} -> Busca os detalhes de um pedido específico pelo seu ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // GET /pedidos/cliente/{clienteId} -> Lista o histórico de pedidos de um cliente específico
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }

    // PATCH /pedidos/{id}/status -> Atualiza parcialmente apenas o status do pedido (ex: para AGUARDADNO_PAGAMENTO, EM_PREPARO, PRONTO)
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> atualizarStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }

    // DELETE /pedidos/{id} -> Cancela um pedido (muda o status para CANCELADO por soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
