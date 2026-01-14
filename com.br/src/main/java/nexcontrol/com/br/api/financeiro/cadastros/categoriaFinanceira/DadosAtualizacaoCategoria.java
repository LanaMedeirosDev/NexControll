package nexcontrol.com.br.api.financeiro.cadastros.categoriaFinanceira;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoCategoria(
        @NotNull
        Long id,
        String nomeCategoria,
        String nomeSubCategoria) {
}
