package nexcontrol.com.br.api.financeiro.cadastros.formasPagamento;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPagamento(
        @NotNull
        Long id,
        String nomePagamento,
        Long idConta) {
}
