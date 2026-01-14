package nexcontrol.com.br.api.financeiro.contasReceber;

import jakarta.validation.Valid;
import nexcontrol.com.br.api.clientes.ClienteRepository;
import nexcontrol.com.br.api.clientes.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {

    @Autowired
    private ReceitasRepository receitasRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody DadosCadastroReceitas dados) {

        Clientes cliente = clienteRepository
                .findById(dados.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Receitas receita = new Receitas(dados, cliente);

        receitasRepository.save(receita);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoReceitas dados){
        var receitas = receitasRepository.getReferenceById(dados.id());
        receitas.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoReceitas(receitas));
    }

    //Endpoint para mudar como recebida
    @PutMapping("/receber")
    @Transactional
    public ResponseEntity receber(@RequestBody @Valid DadosRecebimentoReceita dados) {
        var receita = receitasRepository.getReferenceById(dados.id());
        receita.receber(dados.dataRecebimento());

        return ResponseEntity.ok(new DadosDetalhamentoReceitas(receita));
    }
}
