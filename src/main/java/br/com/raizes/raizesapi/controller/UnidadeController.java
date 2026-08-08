package br.com.raizes.raizesapi.controller;

import br.com.raizes.raizesapi.dto.unidade.UnidadeRequest;
import br.com.raizes.raizesapi.dto.unidade.UnidadeResponse;
import br.com.raizes.raizesapi.service.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
@Tag(name = "Unidades", description = "Endpoints para governança e cadastro de filiais comerciais")
public class UnidadeController {

    private final UnidadeService service;

    // GET /unidades -> Lista todas as unidades cadastras no sistema
    @GetMapping
    @Operation(summary = "Listar filiais", description = "Exibe uma listagem paginada das unidades físicas operacionais da rede")
    public ResponseEntity<org.springframework.data.domain.Page<br.com.raizes.raizesapi.dto.unidade.UnidadeResponse>> listar(
            @org.springframework.data.web.PageableDefault(size = 10, sort = "id") org.springframework.data.domain.Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(pageable));
    }


    // GET /unidades/{id} -> Busca os detalhes informativos de uma filial
    @GetMapping("/{id}")
    @Operation(summary = "Buscar unidade por ID", description = "Recupera as informações completas de localização e status de uma unidade")
    public ResponseEntity<UnidadeResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // POST /unidades -> Registra uma nova filial física (Restrito a ADMIN/GERENTE)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PostMapping
    @Operation(summary = "Cadastrar filial", description = "Insere uma nova unidade comercial validando os contratos de entrada corporativos")
    public ResponseEntity<UnidadeResponse> criar(@Valid @RequestBody UnidadeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    // PUT /unidades/{id} -> Altera os dados cadastrais da filial (Restrito a ADMIN/GERENTE)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filial", description = "Permite a alteração de endereço ou status logístico de operação da filial")
    public ResponseEntity<UnidadeResponse> atualizar(@PathVariable Long id, @Valid @RequestBody UnidadeRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    // DELETE /unidades/{id} -> Remove uma filial do banco (Restrito a ADMIN/GERENTE)
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir filial", description = "Remove permanentemente o registro da unidade da rede de dados relacionais")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
