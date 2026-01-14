package nexcontrol.com.br.api.financeiro.contasPagar;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DespesasRepository extends JpaRepository<Despesas, Long>{
    List<Despesas> findByDataVencimentoDespesaBetween(
            LocalDate inicio,
            LocalDate fim);

    List<Despesas> findByStatusDespesa(StatusDespesa statusDespesa);
}
