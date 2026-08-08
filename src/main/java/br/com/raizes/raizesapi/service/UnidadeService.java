package br.com.raizes.raizesapi.service;

import br.com.raizes.raizesapi.dto.unidade.UnidadeRequest;
import br.com.raizes.raizesapi.dto.unidade.UnidadeResponse;
import br.com.raizes.raizesapi.entity.Unidade;
import br.com.raizes.raizesapi.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository repository;

    public org.springframework.data.domain.Page<br.com.raizes.raizesapi.dto.unidade.UnidadeResponse> listar(org.springframework.data.domain.Pageable pageable) {
        return repository.findAll(pageable).map(this::converterParaResponse);
    }


    public UnidadeResponse buscarPorId(Long id) {
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não localizada."));
        return converterParaResponse(unidade);
    }

    public UnidadeResponse criar(UnidadeRequest request) {
        Unidade unidade = new Unidade();
        unidade.setNome(request.nome());
        unidade.setEndereco(request.endereco());
        unidade.setAtiva(request.ativa());
        return converterParaResponse(repository.save(unidade));
    }

    public UnidadeResponse atualizar(Long id, UnidadeRequest request) {
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não localizada para atualização."));
        unidade.setNome(request.nome());
        unidade.setEndereco(request.endereco());
        unidade.setAtiva(request.ativa());
        return converterParaResponse(repository.save(unidade));
    }

    public void excluir(Long id) {
        Unidade unidade = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não localizada para exclusão."));
        repository.delete(unidade);
    }

    private UnidadeResponse converterParaResponse(Unidade unidade) {
        return new UnidadeResponse(unidade.getId(), unidade.getNome(), unidade.getEndereco(), unidade.getAtiva());
    }
}
