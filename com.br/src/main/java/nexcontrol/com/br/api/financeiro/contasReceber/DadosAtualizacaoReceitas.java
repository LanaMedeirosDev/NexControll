package nexcontrol.com.br.api.financeiro.contasReceber;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosAtualizacaoReceitas(
        @NotNull
        Long id,
        String descricao,
        BigDecimal valorReceita,
        LocalDate dataVencimento,
        Long clienteId,
        StatusReceita statusReceita) {
}
