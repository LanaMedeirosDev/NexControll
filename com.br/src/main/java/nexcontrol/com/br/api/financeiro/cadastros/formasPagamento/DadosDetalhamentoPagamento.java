package nexcontrol.com.br.api.financeiro.cadastros.formasPagamento;

import nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras.Contas;

public record DadosDetalhamentoPagamento(
        Long id,
        String nomePagamento,
        Long idConta
) {
    public DadosDetalhamentoPagamento(Pagamento pagamento, Contas contas){
        this(
                pagamento.getId(),
                pagamento.getNomePagamento(),
                pagamento.getContas().getId());
    }
}
