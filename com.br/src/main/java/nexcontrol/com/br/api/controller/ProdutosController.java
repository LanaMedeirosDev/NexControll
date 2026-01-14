package nexcontrol.com.br.api.controller;

import jakarta.validation.Valid;
import nexcontrol.com.br.api.produtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*") // Permite que o Front-end acesse a API
public class ProdutosController {

    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroProdutos dados, UriComponentsBuilder uriBuilder) {
        var produtos = new Produtos(dados);
        repository.save(produtos);

        var uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produtos.getIdProduto()).toUri();
        return ResponseEntity.created(uri).body(produtos);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemProduto>> listar(
            @PageableDefault(size = 10, sort = {"nomeDoProduto"}) Pageable paginacao) {

        var page = repository.findByAtivoProdutoTrue(paginacao).map(DadosListagemProduto::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizar(@RequestBody @Valid DadosAtualizacaoProduto dados) {
        var produtos = repository.getReferenceById(dados.id());
        produtos.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoProduto(produtos));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        var produtos = repository.getReferenceById(id);
        produtos.excluir();
        return ResponseEntity.noContent().build(); // Alterado para 204, mais correto que notFound()
    }
}