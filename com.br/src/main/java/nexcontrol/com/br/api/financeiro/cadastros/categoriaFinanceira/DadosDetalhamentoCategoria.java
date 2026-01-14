package nexcontrol.com.br.api.financeiro.cadastros.categoriaFinanceira;

import nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras.Contas;

public record DadosDetalhamentoCategoria(
        Long id,
        String nomeCategoria,
        String nomeSubCategoria) {

    public DadosDetalhamentoCategoria(Categoria categoria){
        this(
                categoria.getId(),
                categoria.getNomeCategoria(),
                categoria.getNomeSubCategoria());
    }
}
