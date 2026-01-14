package nexcontrol.com.br.api.financeiro.movimentoFinanceiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movimentoFinanceiro")
public class MovimentoFinanceiroController {

    @Autowired
    private MovimentoFinanceiroService service;

    @GetMapping
    public List<MovimentoFinanceiroDTO> listar(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim
    ) {
        return service.buscarMovimento(inicio, fim);
    }
}
