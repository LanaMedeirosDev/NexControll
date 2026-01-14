package nexcontrol.com.br.api.financeiro.contasReceber;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosRecebimentoReceita(
        @NotNull Long id,
        @NotNull LocalDate dataRecebimento) {
}
