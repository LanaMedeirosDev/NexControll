package nexcontrol.com.br.api.produtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroProdutos(

       @NotBlank
        String nomeDoProduto,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String ncm,

        @NotBlank
        String valorDeVenda,

        String valorDeCusto,

        @NotNull
        ProdutoServico tipoDeCadastroProduto,

        @NotBlank
        String unidadeDeMedida,

        String codigoSku,

        String codigoDeBarras,

        String cfopPreferencial ) {
}
