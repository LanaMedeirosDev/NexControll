package nexcontrol.com.br.api.financeiro.movimentoFinanceiro;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovimentoFinanceiroListar (
        Long id,
        String tipo,
        String descricao,
        BigDecimal valor,
        LocalDate dataVencimento,
        Enum<?> status
){
}
