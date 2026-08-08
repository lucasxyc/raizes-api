package br.com.raizes.raizesapi.controller;

import br.com.raizes.raizesapi.entity.Produto;
import br.com.raizes.raizesapi.service.ProdutoService;
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
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento do catálogo e precificação de itens")
public class ProdutoController {

    private final ProdutoService produtoService;

    // GET /produtos -> Lista todos os produtos cadastrados no cardápio geral
    @GetMapping
    @Operation(summary = "Listar cardápio", description = "Retorna todos os itens gastronômicos e bebidas ativos e cadastrados no catálogo")
    public ResponseEntity<org.springframework.data.domain.Page<Produto>> listarTodos(
            @org.springframework.data.web.PageableDefault(size = 10, sort = "id") org.springframework.data.domain.Pageable pageable
    ) {
        return ResponseEntity.ok(produtoService.listarTodos(pageable));
    }
    
    // GET /produtos/{id} -> Busca as informações detalhadas e o preço de um produto pelo ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Recupera os detalhes cadastrais e o valor em precificação técnica de um produto específico")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    // POST /produtos -> Cadastra um novo produto informando nome, descrição e preço básico
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PostMapping
    @Operation(summary = "Cadastrar produto", description = "Insere uma nova opção de item ou insumo no catálogo de produtos geral")
    public ResponseEntity<Produto> salvar(@Valid @RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    // PUT /produtos/{id} -> Atualiza o preço ou os dados cadastrais de um produto existente
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do produto", description = "Atualiza de forma integral as propriedades cadastrais ou precificação de um produto")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    // DELETE /produtos/{id} -> Remove permanentemente o registro do produto do catálogo
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto", description = "Exclui em definitivo um produto do catálogo físico do banco relacional")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
