package nexcontrol.com.br.api.financeiro.contasPagar;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoDespesas(
        Long id,
        String descricaoDespesa,
        BigDecimal valorDespesa,
        LocalDate dataVencimentoDespesa,
        StatusDespesa statusDespesa,
        Long clienteId,
        String nome
) {

    public DadosDetalhamentoDespesas(Despesas despesa) {
        this(
                despesa.getId(),
                despesa.getDescricaoDespesa(),
                despesa.getValorDespesa(),
                despesa.getDataVencimentoDespesa(),
                despesa.getStatusDespesa(),
                despesa.getCliente().getId(),
                despesa.getCliente().getNome()
        );
    }
}
