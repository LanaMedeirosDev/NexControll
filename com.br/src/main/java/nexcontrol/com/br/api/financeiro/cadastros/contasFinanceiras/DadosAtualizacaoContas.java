package nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoContas(
        @NotNull
        Long idConta,
        String nomeConta) {
}
