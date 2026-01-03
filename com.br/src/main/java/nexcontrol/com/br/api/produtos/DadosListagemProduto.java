package nexcontrol.com.br.api.produtos;

public record DadosListagemProduto(
        String nomeDoProduto,
        String ncm,               // Adicionado
        String valorDeVenda,
        String valorDeCusto,     // Adicionado
        String unidadeDeMedida,
        ProdutoServico tipoDeCadastroProduto,
        String codigoSku,       // Adicionado
        Long id
) {
    public DadosListagemProduto(Produtos produto) {
        this(
                produto.getNomeDoProduto(),
                produto.getNcm(),                // Adicionado
                produto.getValorDeVenda(),
                produto.getValorDeCusto(),      // Adicionado
                produto.getUnidadeDeMedida(),
                produto.getTipoDeCadastroProduto(),
                produto.getCodigoSku(),          // Adicionado
                produto.geiIdProduto()
        );
    }
}