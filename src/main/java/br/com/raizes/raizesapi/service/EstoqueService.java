package br.com.raizes.raizesapi.service;

import br.com.raizes.raizesapi.dto.estoque.EstoqueRequest;
import br.com.raizes.raizesapi.dto.estoque.EstoqueResponse;
import br.com.raizes.raizesapi.entity.Estoque;
import br.com.raizes.raizesapi.entity.Produto;
import br.com.raizes.raizesapi.entity.Unidade;
import br.com.raizes.raizesapi.repository.EstoqueRepository;
import br.com.raizes.raizesapi.repository.ProdutoRepository;
import br.com.raizes.raizesapi.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository;
    private final UnidadeRepository unidadeRepository;

    public org.springframework.data.domain.Page<br.com.raizes.raizesapi.dto.estoque.EstoqueResponse> listar(org.springframework.data.domain.Pageable pageable) {
        return repository.findAll(pageable).map(this::converterParaResponse);
    }


    public EstoqueResponse buscarPorId(Long id) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de estoque não encontrado."));
        return converterParaResponse(estoque);
    }

    @Transactional
    public EstoqueResponse cadastrar(EstoqueRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));

        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada."));

        if (repository.findByProdutoIdAndUnidadeId(request.produtoId(), request.unidadeId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este produto já possui um registro de estoque cadastrado nesta unidade.");
        }

        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setUnidade(unidade);
        estoque.setQuantidadeDisponivel(request.quantidade());

        Estoque estoqueSalvo = repository.save(estoque);
        return converterParaResponse(estoqueSalvo);
    }

    @Transactional
    public EstoqueResponse atualizar(Long id, EstoqueRequest request) {
        Estoque estoque = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de estoque não encontrado."));

        estoque.setQuantidadeDisponivel(request.quantidade());

        Estoque estoqueAtualizado = repository.save(estoque);
        return converterParaResponse(estoqueAtualizado);
    }

    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de estoque não encontrado.");
        }
        repository.deleteById(id);
    }


    public boolean verificarDisponibilidade(Long produtoId, Long unidadeId, Integer quantidade) {
        Estoque estoque = repository.findByProdutoIdAndUnidadeId(produtoId, unidadeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não localizado para este produto nesta unidade."));
        return estoque.getQuantidadeDisponivel() >= quantidade;
    }

    @Transactional
    public void baixarEstoque(Long produtoId, Long unidadeId, Integer quantidade) {
        Estoque estoque = repository.findByProdutoIdAndUnidadeId(produtoId, unidadeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não localizado para este produto nesta unidade."));

        if (estoque.getQuantidadeDisponivel() < quantidade) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Quantidade em estoque insuficiente para baixa.");
        }

        estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() - quantidade);
        repository.save(estoque);
    }

    @Transactional
    public void reporEstoque(Long produtoId, Long unidadeId, Integer quantidade) {
        Estoque estoque = repository.findByProdutoIdAndUnidadeId(produtoId, unidadeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não localizado para este produto nesta unidade."));

        estoque.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel() + quantidade);
        repository.save(estoque);
    }

    private EstoqueResponse converterParaResponse(Estoque estoque) {
        return new EstoqueResponse(
                estoque.getId(),
                estoque.getProduto().getId(),
                estoque.getProduto().getNome(),
                estoque.getUnidade().getId(),
                estoque.getUnidade().getNome(),
                estoque.getQuantidadeDisponivel()
        );
    }
}
