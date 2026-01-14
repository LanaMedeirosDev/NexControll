package nexcontrol.com.br.api.financeiro.contasReceber;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoReceitas(
        Long id,
        String descricao,
        BigDecimal valorReceita,
        LocalDate dataVencimento,
        StatusReceita statusReceita,
        Long clienteId,
        String nome
) {
    public DadosDetalhamentoReceitas (Receitas receitas) {
        this(receitas.getId(),
                receitas.getDescricao(),
                receitas.getValorReceita(),
                receitas.getDataVencimento(),
                receitas.getStatusReceita(),
                receitas.getCliente().getId(),
                receitas.getCliente().getNome());
    }
}
