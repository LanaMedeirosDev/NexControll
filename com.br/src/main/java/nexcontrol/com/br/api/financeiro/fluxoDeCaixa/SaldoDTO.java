package nexcontrol.com.br.api.financeiro.fluxoDeCaixa;

import java.math.BigDecimal;

public record SaldoDTO(
        BigDecimal totalRecebido,
        BigDecimal totalPago,
        BigDecimal saldo ) {
}

