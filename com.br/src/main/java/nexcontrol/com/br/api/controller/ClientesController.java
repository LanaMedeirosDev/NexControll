package nexcontrol.com.br.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nexcontrol.com.br.api.clientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    //Permite conexão com o banco
    private ClienteRepository repository;

    @PostMapping
    @Transactional
    //Para realizar o cadastro do cliente no banco de dados
    public void cadastrar(@RequestBody @Valid DadosCadastroCliente dados) {
        repository.save(new Clientes(dados));
    }

    @GetMapping
    //Para Listar os cadastros e fazer a paginação da forma que eu determinar
    public Page<DadosListagemCliente> listar(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemCliente::new);
    }

    @PutMapping
    @Transactional
    //Para atualizar o cadastro do client permitindo alteração no campo que ele quiser
    public void atualizar(@RequestBody @Valid DadosAtualizacaoCliente dados){
        var cliente = repository.getReferenceById(dados.id());
        cliente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    //Para inativar um produto sem apagar totalmente do banco
    // -> Path Variable serve para simbolizar ao spring o dado da URL que queremos buscar
    public void excluir(@PathVariable Long id){
        var cliente = repository.getReferenceById(id);
        cliente.excluir();
    }
}

