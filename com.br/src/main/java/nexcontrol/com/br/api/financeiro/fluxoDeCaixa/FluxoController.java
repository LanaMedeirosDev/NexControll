package nexcontrol.com.br.api.financeiro.fluxoDeCaixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FluxoController {

    @Autowired
    private FluxoService fluxoService;

    @GetMapping("/fluxodecaixa")
    public SaldoDTO calcularSaldo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {

        return fluxoService.calcularSaldo(inicio, fim);
    }

    // Verificação para o cálculo e relatório Diário -> traz o dia e o ano
    @GetMapping("/saldo/diario")
    public SaldoDTO obterSaldoDiario(@RequestParam String data) {
        LocalDate dia = LocalDate.parse(data, DateTimeFormatter.ISO_DATE);
        return fluxoService.calcularSaldoDiario(dia);
    }

    //  Verificação para o cálculo e relatório Mensal -> traz o mês e ano
    @GetMapping("/saldo/mensal")
    public SaldoDTO obterSaldoMensal(@RequestParam int mes, @RequestParam int ano) {
        return fluxoService.calcularSaldoMensal(mes, ano);
    }
}
