package nexcontrol.com.br.api.financeiro.contasReceber;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReceitasService {
    private final ReceitasRepository receitasRepository;

    public ReceitasService(ReceitasRepository receitasRepository) {
        this.receitasRepository = receitasRepository;
}
    //Permite que atualize todo dia 00h
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void atualizarReceitasAtrasadas() {
        var receitas = receitasRepository.findByStatusReceita(StatusReceita.PREVISTA);
        receitas.forEach(Receitas::verificarAtraso);
    }

}

