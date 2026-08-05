package br.com.raizes.raizesapi.controller;

import br.com.raizes.raizesapi.dto.pagamento.PagamentoRequest;
import br.com.raizes.raizesapi.dto.pagamento.PagamentoResponse;
import br.com.raizes.raizesapi.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService service;

    // POST /pagamentos -> Processa a simulação do Mock Gateway autorizando ou recusando a transação
    @PostMapping
    public ResponseEntity<PagamentoResponse> processar(@RequestBody PagamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.processarPagamento(request));
    }

    // GET /pagamentos -> Lista o histórico de todas as tentativas e transações de pagamentos
    @GetMapping
    public ResponseEntity<List<PagamentoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET /pagamentos/{id} -> Busca os detalhes de uma transação específica pelo ID do pagamento
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
