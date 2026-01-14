package nexcontrol.com.br.api.produtos;

public record DadosListagemProduto(
        String nomeDoProduto,
        String valorDeVenda,
        String unidadeDeMedida,
        ProdutoServico tipoDeCadastroProduto,
        String codigoDeBarras,
        String cfopPreferencial,
        String ncm,
        String valorDeCusto,
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