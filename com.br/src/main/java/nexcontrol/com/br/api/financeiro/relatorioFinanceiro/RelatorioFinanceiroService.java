package nexcontrol.com.br.api.financeiro.relatorioFinanceiro;

import nexcontrol.com.br.api.financeiro.contasPagar.Despesas;
import nexcontrol.com.br.api.financeiro.contasPagar.DespesasRepository;
import nexcontrol.com.br.api.financeiro.contasPagar.StatusDespesa;
import nexcontrol.com.br.api.financeiro.contasReceber.Receitas;
import nexcontrol.com.br.api.financeiro.contasReceber.ReceitasRepository;
import nexcontrol.com.br.api.financeiro.contasReceber.StatusReceita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class RelatorioFinanceiroService {

    @Autowired
    private ReceitasRepository receitasRepository;

    @Autowired
    private DespesasRepository despesasRepository;

    public RelatorioSemanalDTO gerarRelatorioSemanal() {

        LocalDate inicioSemana = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate fimSemana = inicioSemana.plusDays(6);

        var receitas = receitasRepository
                .findByDataVencimentoBetween(inicioSemana, fimSemana);

        var despesas = despesasRepository
                .findByDataVencimentoDespesaBetween(inicioSemana, fimSemana);

        BigDecimal totalReceitas = receitas.stream()
                .map(Receitas::getValorReceita)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = despesas.stream()
                .map(Despesas::getValorDespesa)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        BigDecimal receitasRecebidas = receitas.stream()
                .filter(r -> r.getStatusReceita() == StatusReceita.RECEBIDA)
                .map(Receitas::getValorReceita)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal despesasPagas = despesas.stream()
                .filter(d -> d.getStatusDespesa() == StatusDespesa.PAGA)
                .map(Despesas::getValorDespesa)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RelatorioSemanalDTO(
                inicioSemana,
                fimSemana,
                totalReceitas,
                totalDespesas,
                saldo,
                receitasRecebidas,
                BigDecimal.ZERO, // previstas (você completa depois)
                BigDecimal.ZERO, // atrasadas
                despesasPagas,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
    }
}
