package br.com.raizes.raizesapi.service.estoque;

import br.com.raizes.raizesapi.dto.estoque.EstoqueRequest;
import br.com.raizes.raizesapi.dto.estoque.EstoqueResponse;
import br.com.raizes.raizesapi.entity.Estoque;
import br.com.raizes.raizesapi.entity.Produto;
import br.com.raizes.raizesapi.repository.EstoqueRepository;
import br.com.raizes.raizesapi.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository;

    public List<EstoqueResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public EstoqueResponse buscarPorId(Long id) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de estoque não encontrado."));
        return converterParaResponse(estoque);
    }

    @Transactional
    public EstoqueResponse cadastrar(EstoqueRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        // Evita duplicar estoque para o mesmo produto
        if(repository.findByProdutoId(request.produtoId()).isPresent()) {
            throw new RuntimeException("Este produto já possui um registro de estoque.");
        }

        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setQuantidadeDisponivel(request.quantidade());

        Estoque estoqueSalvo = repository.save(estoque);
        return converterParaResponse(estoqueSalvo);
    }

    @Transactional
    public EstoqueResponse atualizar(Long id, EstoqueRequest request) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de estoque não encontrado."));

        estoque.setQuantidadeDisponivel(request.quantidade());

        Estoque estoqueAtualizado = repository.save(estoque);
        return converterParaResponse(estoqueAtualizado);
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Item de estoque não encontrado.");
        }
        repository.deleteById(id);
    }

    // Regras de Negócio específicas para a Etapa de Pedidos

    public boolean verificarDisponibilidade(Long produtoId, Integer quantidade) {
        Estoque estoque = repository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não localizado para este produto."));
        return estoque.getQuantidadeDisponivel() >= quantidade;
    }

    @Transactional
    public void baixarEstoque(Long produtoId, Integer quantidade) {
        Estoque estoque = repository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não localizado para este produto."));

        if (estoque.getQuantidadeDisponivel() < quantidade) {
            throw new RuntimeException("Quantidade em estoque insuficiente para baixa.");
        }

        estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() - quantidade);
        repository.save(estoque);
    }

    @Transactional
    public void reporEstoque(Long produtoId, Integer quantidade) {
        Estoque estoque = repository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não localizado para este produto."));

        estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() + quantidade);
        repository.save(estoque);
    }

    private EstoqueResponse converterParaResponse(Estoque estoque) {
        return new EstoqueResponse(
                estoque.getId(),
                estoque.getProduto().getId(),
                estoque.getProduto().getNome(),
                estoque.getQuantidadeDisponivel()
        );
    }
}
