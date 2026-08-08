package br.com.raizes.raizesapi.service;

import br.com.raizes.raizesapi.dto.pedido.*;
import br.com.raizes.raizesapi.entity.*;
import br.com.raizes.raizesapi.enums.StatusPedido;
import br.com.raizes.raizesapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;
    private final EstoqueService estoqueService;

    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request) {
        if (request.canalPedido() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo canalPedido é obrigatório.");
        }

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada."));

        if (request.itens() == null || request.itens().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O pedido deve conter pelo menos um item.");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setUnidade(unidade);
        pedido.setFormaPagamento(request.formaPagamento());
        pedido.setCanalPedido(request.canalPedido());
        pedido.setItens(new ArrayList<>());

        BigDecimal valorTotalPedido = BigDecimal.ZERO;

        for (ItemPedidoRequest itemReq : request.itens()) {
            if (itemReq.quantidade() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade do produto deve ser maior que zero.");
            }

            Produto produto = produtoRepository.findById(itemReq.produtoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));

            boolean temEstoque = estoqueService.verificarDisponibilidade(produto.getId(), unidade.getId(), itemReq.quantidade());
            if (!temEstoque) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Estoque insuficiente para o produto: " + produto.getNome() + " na unidade: " + unidade.getNome());
            }

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemReq.quantidade());

            BigDecimal precoUnitario = produto.getPreco();
            itemPedido.setPrecoUnitario(precoUnitario);

            BigDecimal subtotal = precoUnitario.multiply(BigDecimal.valueOf(itemReq.quantidade()));
            valorTotalPedido = valorTotalPedido.add(subtotal);

            pedido.getItens().add(itemPedido);
        }

        pedido.setValorTotal(valorTotalPedido);
        Pedido pedidoSalvo = repository.save(pedido);

        return converterParaResponse(pedidoSalvo);
    }

    public List<PedidoResponse> listar() {
        return repository.findAll().stream().map(this::converterParaResponse).collect(Collectors.toList());
    }

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
        return converterParaResponse(pedido);
    }

    public List<PedidoResponse> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId).stream().map(this::converterParaResponse).collect(Collectors.toList());
    }

    @Transactional
    public PedidoResponse atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
        pedido.setStatus(novoStatus);
        return converterParaResponse(repository.save(pedido));
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
        pedido.setStatus(StatusPedido.CANCELADO);
        repository.save(pedido);
    }

    private PedidoResponse converterParaResponse(Pedido pedido) {
        List<ItemPedidoResponse> itensResponse = pedido.getItens().stream()
                .map(item -> new ItemPedidoResponse(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario()
                )).collect(Collectors.toList());

        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getFormaPagamento(),
                pedido.getCanalPedido(),
                itensResponse
        );


    }

    public List<PedidoResponse> listarPorCanal(br.com.raizes.raizesapi.enums.CanalPedido canalPedido) {
        return repository.findByCanalPedido(canalPedido)
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

}
