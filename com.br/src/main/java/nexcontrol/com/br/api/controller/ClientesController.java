package nexcontrol.com.br.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nexcontrol.com.br.api.clientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    //Permite conexão com o banco
    private ClienteRepository repository;

    @PostMapping
    @Transactional
    //Para realizar o cadastro do cliente no banco de dados
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCliente dados, UriComponentsBuilder uriBuilder) {
        var cliente = new Clientes(dados);
        repository.save(cliente);

        var uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).body(cliente);
    }

    @GetMapping
    //Para Listar os cadastros e fazer a paginação da forma que eu determinar
    public ResponseEntity<Page<DadosListagemCliente>> listar(@PageableDefault(size = 10, sort = {"nome"})Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemCliente::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    //Para atualizar o cadastro do client permitindo alteração no campo que ele quiser
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoCliente dados){
        var cliente = repository.getReferenceById(dados.id());
        cliente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    //Para inativar um produto sem apagar totalmente do banco
    // -> Path Variable serve para simbolizar ao spring o dado da URL que queremos buscar
    //Response Entity serve para controlar o retorno da API para forma de que desejar (boas práticas)
    //No Content é o código 204 da API e mostra que a ação foi concluída com sucesso mas que o retono é vazio
    public ResponseEntity excluir(@PathVariable Long id){
        var cliente = repository.getReferenceById(id);
        cliente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var cliente = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoCliente(cliente));
    }
}