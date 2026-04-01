package nexcontrol.com.br.api.financeiro.contasPagar;

import jakarta.validation.Valid;
import nexcontrol.com.br.api.clientes.ClienteRepository;
import nexcontrol.com.br.api.clientes.Clientes;
import nexcontrol.com.br.api.financeiro.contasReceber.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/Despesas")
public class DespesasController {

    @Autowired
    private DespesasRepository despesasRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody DadosCadastroDespesas dados) {

        Clientes cliente = clienteRepository
                .findById(dados.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Despesas despesa = new Despesas(dados, cliente);

        despesasRepository.save(despesa);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoDespesas dados){
        var despesas = despesasRepository.getReferenceById(dados.id());
        despesas.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoDespesas(despesas));
    }

    @PutMapping("/pagar")
    @Transactional
    public ResponseEntity pagar(@RequestBody @Valid DadosRecebimentoDespesas dados) {
        var despesa = despesasRepository.getReferenceById(dados.id());
        despesa.pagar(dados.dataPagamento());

        return ResponseEntity.ok(new DadosDetalhamentoDespesas(despesa));
    }
}
