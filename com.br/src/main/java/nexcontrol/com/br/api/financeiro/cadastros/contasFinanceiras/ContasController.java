package nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras;

import jakarta.validation.Valid;
import nexcontrol.com.br.api.produtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/contas")
@CrossOrigin(origins = "*")
public class ContasController {

    @Autowired
    private ContasRepository contasRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroContas dados, UriComponentsBuilder uriBuilder) {
        var contas = new Contas(dados);
        contasRepository.save(contas);

        var uri = uriBuilder.path("/contas/{id}").buildAndExpand(contas.getId()).toUri();

        return ResponseEntity.created(uri).body(contas);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoContas dados){
        var contas = contasRepository.getReferenceById(dados.idConta());
        contas.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoContas(contas));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var contas = contasRepository.getReferenceById(id);
        contas.excluir();

        return ResponseEntity.notFound().build();
    }
}
