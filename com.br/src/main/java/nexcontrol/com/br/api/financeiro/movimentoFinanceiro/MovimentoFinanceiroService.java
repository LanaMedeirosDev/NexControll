package nexcontrol.com.br.api.financeiro.movimentoFinanceiro;

import nexcontrol.com.br.api.financeiro.contasPagar.Despesas;
import nexcontrol.com.br.api.financeiro.contasPagar.DespesasRepository;
import nexcontrol.com.br.api.financeiro.contasReceber.Receitas;
import nexcontrol.com.br.api.financeiro.contasReceber.ReceitasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MovimentoFinanceiroService {

    @Autowired
    private ReceitasRepository receitasRepository;

    @Autowired
    private DespesasRepository despesasRepository;

    public List<MovimentoFinanceiroDTO> buscarMovimento(
            LocalDate inicio,
            LocalDate fim
    ) {

        List<MovimentoFinanceiroDTO> movimentos = new ArrayList<>();

        receitasRepository
                .findByDataVencimentoBetween(inicio, fim)
                .forEach(r -> movimentos.add(
                        new MovimentoFinanceiroDTO(
                                r.getId(),
                                "RECEBER",
                                r.getDescricao(),
                                r.getValorReceita(),
                                r.getDataVencimento(),
                                r.getStatusReceita()
                        )
                ));

        despesasRepository
                .findByDataVencimentoDespesaBetween(inicio, fim)
                .forEach(d -> movimentos.add(
                        new MovimentoFinanceiroDTO(
                                d.getId(),
                                "PAGAR",
                                d.getDescricaoDespesa(),
                                d.getValorDespesa(),
                                d.getDataVencimentoDespesa(),
                                d.getStatusDespesa()
                        )
                ));

        movimentos.sort(
                Comparator.comparing(MovimentoFinanceiroDTO::dataVencimento)
        );

        return movimentos;
    }
}
