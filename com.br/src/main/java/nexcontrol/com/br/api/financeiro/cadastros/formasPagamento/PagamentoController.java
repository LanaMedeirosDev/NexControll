package nexcontrol.com.br.api.financeiro.cadastros.formasPagamento;

import jakarta.validation.Valid;
import nexcontrol.com.br.api.clientes.Clientes;
import nexcontrol.com.br.api.financeiro.cadastros.categoriaFinanceira.*;
import nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras.Contas;
import nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = "*")
public class PagamentoController {

    @Autowired
    private  PagamentoRepository pagamentoRepository;

    @Autowired
    private ContasRepository contasRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPagamento dados, UriComponentsBuilder uriBuilder) {

        Contas contas = contasRepository
                .findById(dados.idConta())
                .orElseThrow(() -> new RuntimeException("Conta não encontrado"));

        var pagamento = new Pagamento(dados, contas);
        pagamentoRepository.save(pagamento);

        var uri = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(uri).body(pagamento);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPagamento dados, Contas contas){
        var pagamento = pagamentoRepository.getReferenceById(dados.id());
        pagamento.atualizarInformacoes(dados, contas);

        return ResponseEntity.ok(new DadosDetalhamentoPagamento(pagamento, contas));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var pagamento = pagamentoRepository.getReferenceById(id);
        pagamento.excluir();

        return ResponseEntity.notFound().build();
    }
}
