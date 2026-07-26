package br.com.raizes.raizesapi.service.cliente;

import br.com.raizes.raizesapi.dto.cliente.ClienteRequest;
import br.com.raizes.raizesapi.dto.cliente.ClienteResponse;
import br.com.raizes.raizesapi.entity.Cliente;
import br.com.raizes.raizesapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public List<ClienteResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }

    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        return converterParaResponse(cliente);
    }

    public ClienteResponse criar(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());
        cliente.setConsentimentoLGPD(request.getConsentimentoLGPD());

        Cliente clienteSalvo = repository.save(cliente);
        return converterParaResponse(clienteSalvo);
    }

    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());
        cliente.setConsentimentoLGPD(request.getConsentimentoLGPD());

        Cliente clienteAtualizado = repository.save(cliente);
        return converterParaResponse(clienteAtualizado);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado.");
        }
        repository.deleteById(id);
    }

    // Método auxiliar (evitar repetição de código)
    private ClienteResponse converterParaResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNome(cliente.getNome());
        response.setEmail(cliente.getEmail());
        response.setTelefone(cliente.getTelefone());
        response.setPontos(cliente.getPontos());
        response.setConsentimentoLGPD(cliente.getConsentimentoLGPD());
        return response;
    }

}