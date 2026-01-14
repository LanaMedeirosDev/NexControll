package nexcontrol.com.br.api.produtos;

import nexcontrol.com.br.api.clientes.Clientes;

public record DadosDetalhamentoProduto(
        Long id,
        String nomeDoProduto,
        String ncm,
        String valorDeVenda,
        String valorDeCusto,
        String unidadeDeMedida,
        ProdutoServico tipoDeCadastroProduto,
        String codigoSku,
        String codigoDeBarras,
        String cfopPreferencial ){

    public DadosDetalhamentoProduto(Produtos produtos){
        this(produtos.getIdProduto(),
                produtos.getNomeDoProduto(),
                produtos.getNcm(),
                produtos.getValorDeVenda(),
                produtos.getValorDeCusto(),
                produtos.getUnidadeDeMedida(),
                produtos.getTipoDeCadastroProduto(),
                produtos.getCodigoSku(),
                produtos.getCodigoDeBarras(),
                produtos.getCfopPreferencial());
    }
}
