package nexcontrol.com.br.api.controller;

import jakarta.validation.Valid;
import nexcontrol.com.br.api.clientes.Clientes;
import nexcontrol.com.br.api.clientes.DadosAtualizacaoCliente;
import nexcontrol.com.br.api.clientes.DadosCadastroCliente;
import nexcontrol.com.br.api.produtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*") // Permite que o seu Front-end acesse a API
public class ProdutosController {

    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroProdutos dados) {
        repository.save(new Produtos(dados));
    }

    @GetMapping
    public Page<DadosListagemProduto> listar(Pageable paginacao) {
        return repository.findByAtivoProdutoTrue(paginacao).map(DadosListagemProduto::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoProduto dados){
        var produtos = repository.getReferenceById(dados.id());
        produtos.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var produtos = repository.getReferenceById(id);
        produtos.excluir();
    }
}