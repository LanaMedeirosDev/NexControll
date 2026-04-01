package nexcontrol.com.br.api.produtos;

import java.math.BigDecimal;

public record DadosListagemProduto(
        String nomeDoProduto,
        BigDecimal valorDeVenda,
        String unidadeDeMedida,
        ProdutoServico tipoDeCadastroProduto,
        String codigoDeBarras,
        String cfopPreferencial,
        String ncm,
        BigDecimal valorDeCusto,
        Long id
) {
    public DadosListagemProduto(Produtos produto) {
        this(
                produto.getNomeDoProduto(),
                produto.getValorDeVenda(),
                produto.getUnidadeDeMedida(),
                produto.getTipoDeCadastroProduto(),
                produto.getCodigoDeBarras(),
                produto.getCfopPreferencial(),
                produto.getNcm(),
                produto.getValorDeCusto(),
                produto.getIdProduto()
        );
    }
}