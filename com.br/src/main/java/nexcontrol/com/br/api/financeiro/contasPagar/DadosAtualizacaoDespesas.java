package nexcontrol.com.br.api.financeiro.contasPagar;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosAtualizacaoDespesas(
        @NotNull
        Long id,
        String descricaoDespesa,
        BigDecimal valorDespesa,
        LocalDate dataVencimentoDespesa,
        Long clienteId,
        StatusDespesa statusDespesa
) {
}
