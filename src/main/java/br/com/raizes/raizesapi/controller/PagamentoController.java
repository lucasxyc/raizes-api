package br.com.raizes.raizesapi.controller;

import br.com.raizes.raizesapi.dto.pagamento.PagamentoRequest;
import br.com.raizes.raizesapi.dto.pagamento.PagamentoResponse;
import br.com.raizes.raizesapi.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
@Tag(name = "Pagamentos", description = "Endpoints para simulação e processamento de transações financeiras")
public class PagamentoController {

    private final PagamentoService service;

    // POST /pagamentos -> Processa a simulação do Mock Gateway autorizando ou recusando a transação
    @PostMapping
    @Operation(summary = "Processar pagamento", description = "Simula a comunicação com um gateway externo, atualiza o pedido, manipula o estoque e pontua fidelidade")
    public ResponseEntity<PagamentoResponse> processar(@Valid @RequestBody PagamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.processarPagamento(request));
    }

    // GET /pagamentos -> Lista o histórico de todas as tentativas e transações de pagamentos
    @GetMapping
    @Operation(summary = "Listar pagamentos", description = "Recupera o histórico completo de transações financeiras de forma paginada")
    public ResponseEntity<org.springframework.data.domain.Page<br.com.raizes.raizesapi.dto.pagamento.PagamentoResponse>> listar(
            @org.springframework.data.web.PageableDefault(size = 10, sort = "id") org.springframework.data.domain.Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    // GET /pagamentos/{id} -> Busca os detalhes de uma transação específica pelo ID do pagamento
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID", description = "Recupera os detalhes informativos de uma transação de pagamento específica através do ID")
    public ResponseEntity<PagamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
