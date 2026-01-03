package nexcontrol.com.br.api.produtos;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProduto(

        @NotNull
        Long id,
        String nomeDoProduto,
        String ncm,
        String valorDeVenda,
        String valorDeCusto,
        ProdutoServico tipoDeCadastroProduto,
        String unidadeDeMedida,
        String codigoSku,
        String codigoDeBarras,
        String cfopPreferencial) {
}
