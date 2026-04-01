package nexcontrol.com.br.api.produtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosAtualizacaoProduto(

        @NotNull
        Long id,
        String nomeDoProduto,
        String ncm,
        BigDecimal valorDeVenda,
        BigDecimal valorDeCusto,
        ProdutoServico tipoDeCadastroProduto,
        String unidadeDeMedida,
        String codigoSku,
        String codigoDeBarras,
        String cfopPreferencial) {
}
