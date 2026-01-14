package nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras;

public record DadosDetalhamentoContas(
        Long idConta,
        String nomeConta
) {
    public DadosDetalhamentoContas(Contas contas){
        this(
                contas.getId(),
                contas.getNomeConta());
    }
}
