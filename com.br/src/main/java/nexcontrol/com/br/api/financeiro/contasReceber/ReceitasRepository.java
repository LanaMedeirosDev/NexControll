package nexcontrol.com.br.api.financeiro.contasReceber;

import nexcontrol.com.br.api.financeiro.contasPagar.Despesas;
import nexcontrol.com.br.api.financeiro.contasPagar.StatusDespesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReceitasRepository extends JpaRepository<Receitas, Long>{
    List<Receitas> findByDataVencimentoBetween(
            LocalDate inicio,
            LocalDate fim);

    List<Receitas> findByStatusReceita(StatusReceita statusReceita);
}
