package br.com.raizes.raizesapi.controller;

import br.com.raizes.raizesapi.dto.pedido.PedidoRequest;
import br.com.raizes.raizesapi.dto.pedido.PedidoResponse;
import br.com.raizes.raizesapi.enums.CanalPedido;
import br.com.raizes.raizesapi.enums.StatusPedido;
import br.com.raizes.raizesapi.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento do fluxo, canais de venda e estados dos pedidos")
public class PedidoController {

    private final PedidoService service;

    // POST /pedidos -> Cria um novo pedido com os produtos selecionados
    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Registra um pedido no sistema validando obrigatoriamente a multicanalidade pelo campo canalPedido")
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPedido(request));
    }

    // GET /pedidos -> Lista todos os pedidos cadastrados no sistema ou filtra opcionalmente por canal de venda
    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma listagem completa de todos os pedidos efetuados ou filtrados por canal de venda (ex: ?canalPedido=TOTEM)")
    public ResponseEntity<List<PedidoResponse>> listar(
            @RequestParam(required = false) CanalPedido canalPedido
    ) {
        if (canalPedido != null) {
            return ResponseEntity.ok(service.listarPorCanal(canalPedido));
        }
        return ResponseEntity.ok(service.listar());
    }

    // GET /pedidos/{id} -> Busca os detalhes de um pedido específico pelo seu ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Recupera as informações detalhadas e o somatório total de um pedido específico")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // GET /pedidos/cliente/{clienteId} -> Lista o histórico de pedidos de um cliente específico
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar pedidos por cliente", description = "Filtra e retorna o histórico completo de compras realizadas por um determinado cliente")
    public ResponseEntity<List<PedidoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }

    // PATCH /pedidos/{id}/status -> Atualiza parcialmente apenas o status do pedido
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Realiza a transição de estado do pedido de forma parcial (ex: EM_PREPARO, PRONTO, ENTREGUE)")
    public ResponseEntity<PedidoResponse> atualizarStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }

    // DELETE /pedidos/{id} -> Cancela um pedido (muda o status para CANCELADO por soft delete)
    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pedido", description = "Aplica a regra de soft delete alterando internamente o estado do pedido para CANCELADO")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
