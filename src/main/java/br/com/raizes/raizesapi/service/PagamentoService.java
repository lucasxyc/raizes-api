package br.com.raizes.raizesapi.service;

import br.com.raizes.raizesapi.dto.pagamento.PagamentoRequest;
import br.com.raizes.raizesapi.dto.pagamento.PagamentoResponse;
import br.com.raizes.raizesapi.entity.Cliente;
import br.com.raizes.raizesapi.entity.ItemPedido;
import br.com.raizes.raizesapi.entity.Pagamento;
import br.com.raizes.raizesapi.entity.Pedido;
import br.com.raizes.raizesapi.enums.StatusPedido;
import br.com.raizes.raizesapi.enums.StatusPagamento;
import br.com.raizes.raizesapi.repository.ClienteRepository;
import br.com.raizes.raizesapi.repository.PagamentoRepository;
import br.com.raizes.raizesapi.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final EstoqueService estoqueService;

    @Transactional
    public PagamentoResponse processarPagamento(PagamentoRequest request) {
        Pedido pedido = pedidoRepository.findById(request.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado para processar o pagamento."));

        if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new RuntimeException("Este pedido não está aguardando pagamento ou já foi processado.");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(pedido.getFormaPagamento());
        pagamento.setValor(pedido.getValorTotal());

        if (request.aprovado()) {
            pagamento.setStatus(StatusPagamento.APROVADO);
            pedido.setStatus(StatusPedido.EM_PREPARO);

            for (ItemPedido item : pedido.getItens()) {
                estoqueService.baixarEstoque(item.getProduto().getId(), item.getQuantidade());
            }

            Cliente cliente = pedido.getCliente();
            int pontosAtuais = (cliente.getPontos() != null) ? cliente.getPontos() : 0;
            cliente.setPontos(pontosAtuais + 10);
            clienteRepository.save(cliente);

        } else {
            pagamento.setStatus(StatusPagamento.RECUSADO);
            pedido.setStatus(StatusPedido.CANCELADO);
        }

        pedidoRepository.save(pedido);
        Pagamento pagamentoSalvo = repository.save(pagamento);

        return converterParaResponse(pagamentoSalvo);
    }

    public List<PagamentoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public PagamentoResponse buscarPorId(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de pagamento não localizado."));
        return converterParaResponse(pagamento);
    }

    private PagamentoResponse converterParaResponse(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(),
                pagamento.getPedido().getId(),
                pagamento.getFormaPagamento(),
                pagamento.getValor(),
                pagamento.getStatus(),
                pagamento.getDataPagamento()
        );
    }
}
