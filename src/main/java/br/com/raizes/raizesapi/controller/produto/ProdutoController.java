package br.com.raizes.raizesapi.controller.produto;

import br.com.raizes.raizesapi.entity.Produto;
import br.com.raizes.raizesapi.service.produto.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor // Injeta o service pelo construtor automaticamente mantendo o padrão do projeto
public class ProdutoController {

    private final ProdutoService produtoService;

    // GET /produtos -> Lista todos os produtos cadastrados no cardápio geral
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    // GET /produtos/{id} -> Busca as informações detalhadas e o preço de um produto pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    // POST /produtos -> Cadastra um novo produto informando nome, descrição e preço básico
    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    // PUT /produtos/{id} -> Atualiza o preço ou os dados cadastrais de um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    // DELETE /produtos/{id} -> Remove permanentemente o registro do produto do catálogo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
