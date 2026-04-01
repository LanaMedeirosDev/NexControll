package nexcontrol.com.br.api.financeiro.fluxoDeCaixa;

import nexcontrol.com.br.api.financeiro.contasPagar.Despesas;
import nexcontrol.com.br.api.financeiro.contasPagar.DespesasRepository;
import nexcontrol.com.br.api.financeiro.contasPagar.StatusDespesa;
import nexcontrol.com.br.api.financeiro.contasReceber.Receitas;
import nexcontrol.com.br.api.financeiro.contasReceber.ReceitasRepository;
import nexcontrol.com.br.api.financeiro.contasReceber.StatusReceita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import  nexcontrol.com.br.api.financeiro.fluxoDeCaixa.FluxoService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class FluxoService {

    //Comando que permite a comunicação com o Repository
    @Autowired
    private ReceitasRepository receitasRepository;

    @Autowired
    private DespesasRepository despesasRepository;

    //Serve para o cálculo do Saldo Total e cria as variáveis necessárias para o cálculo
    public SaldoDTO calcularSaldo(LocalDate inicio, LocalDate fim) {

        var receitas = receitasRepository
                .findByStatusReceitaAndDataRecebimentoBetween(
                        StatusReceita.RECEBIDA,
                        inicio,
                        fim);
        var despesas = despesasRepository
                .findByStatusDespesaAndDataPagamentoBetween(
                        StatusDespesa.PAGA,
                        inicio,
                        fim);

        BigDecimal totalRecebido = receitas.stream()
                .map(Receitas::getValorReceita)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPago = despesas.stream()
                .map(Despesas::getValorDespesa)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldo = totalRecebido.subtract(totalPago);

        return new SaldoDTO(totalRecebido, totalPago, saldo);
    }

    // Cálculo do Saldo Diário - Service
    public SaldoDTO calcularSaldoDiario(LocalDate dia) {
        BigDecimal totalRecebido = new BigDecimal("200"); // somatório de receitas do dia
        BigDecimal totalPago = new BigDecimal("50");      // somatório de despesas do dia
        BigDecimal saldo = totalRecebido.subtract(totalPago);

        return new SaldoDTO(totalRecebido, totalPago, saldo);
    }

    // Cálculo do saldo mensal - Service
    public SaldoDTO calcularSaldoMensal(int mes, int ano) {
        BigDecimal totalRecebido = new BigDecimal("800"); // somatório de receitas do mês
        BigDecimal totalPago = new BigDecimal("300");     // somatório de despesas do mês
        BigDecimal saldo = totalRecebido.subtract(totalPago);

        return new SaldoDTO(totalRecebido, totalPago, saldo);
    }
}
