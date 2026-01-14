package nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContasRepository extends JpaRepository<Contas, Long>{
    Page<Contas> findAllByAtivoContaTrue(Pageable paginacao);
}
