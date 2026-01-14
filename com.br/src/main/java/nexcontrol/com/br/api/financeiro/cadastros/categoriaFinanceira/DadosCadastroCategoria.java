package nexcontrol.com.br.api.financeiro.cadastros.categoriaFinanceira;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCategoria(

        @NotBlank
        String nomeCategoria,

        @NotBlank
        String nomeSubCategoria) {
}
