package nexcontrol.com.br.api.financeiro.contasPagar;

import nexcontrol.com.br.api.clientes.ClienteRepository;
import nexcontrol.com.br.api.clientes.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {

    @Autowired
    private ReceitasRepository receitasRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroReceitas dados) {

        Clientes cliente = clienteRepository
                .findById(dados.getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

    }
}
