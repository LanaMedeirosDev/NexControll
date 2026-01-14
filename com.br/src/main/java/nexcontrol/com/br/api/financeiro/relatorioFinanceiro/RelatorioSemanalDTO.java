package nexcontrol.com.br.api.financeiro.relatorioFinanceiro;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RelatorioSemanalDTO (
        LocalDate dataInicio,
        LocalDate dataFim,

        BigDecimal totalReceitas,
        BigDecimal totalDespesas,
        BigDecimal saldoSemana,

        BigDecimal receitasRecebidas,
        BigDecimal receitasPrevistas,
        BigDecimal receitasAtrasadas,

        BigDecimal despesasPagas,
        BigDecimal despesasPrevistas,
        BigDecimal despesasAtrasadas
){
}
