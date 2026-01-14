package nexcontrol.com.br.api.financeiro.relatorioFinanceiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class RelatorioFinanceiroScheduler {

    @Autowired
    private RelatorioFinanceiroService service;

    // Toda segunda-feira às 08:00
    @Scheduled(cron = "0 0 12 ? * SUN", zone = "America/Sao_Paulo")
    public void gerarRelatorioSemanalAutomatico() {

        var relatorio = service.gerarRelatorioSemanal();

        System.out.println("Relatório semanal gerado: " + relatorio);
    }
}
