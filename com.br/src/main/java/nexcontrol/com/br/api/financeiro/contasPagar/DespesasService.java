package nexcontrol.com.br.api.financeiro.contasPagar;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

public class DespesasService {
    private final DespesasRepository despesasRepository;

    public DespesasService(DespesasRepository despesasRepository) {
        this.despesasRepository = despesasRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void atualizarReceitasAtrasadas() {
        var despesas = despesasRepository.findByStatusDespesa(StatusDespesa.PREVISTA);
        despesas.forEach(Despesas::verificarAtraso);
    }
}
