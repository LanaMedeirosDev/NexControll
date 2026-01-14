package nexcontrol.com.br.api.financeiro.contasPagar;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record DadosRecebimentoDespesas(
        @NotNull Long id,
        @NotNull LocalDate dataPagamento
) {
}
