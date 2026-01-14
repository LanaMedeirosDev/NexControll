package nexcontrol.com.br.api.financeiro.contasPagar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import nexcontrol.com.br.api.financeiro.contasReceber.StatusReceita;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosCadastroDespesas(
        @NotBlank
        String descricaoDespesa,

        @NotBlank
        BigDecimal valorDespesa,

        LocalDate dataVencimentoDespesa,

        @NotNull
        StatusReceita statusDespesa,

        Long clienteId) {
}
