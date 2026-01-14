package nexcontrol.com.br.api.financeiro.contasReceber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosCadastroReceitas(

        @NotBlank
        String descricao,

        @NotBlank
        BigDecimal valorReceita,

        LocalDate dataVencimento,

        @NotNull
        StatusReceita statusReceita,

        Long clienteId) {
}
