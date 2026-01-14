package nexcontrol.com.br.api.financeiro.contasPagar;

import nexcontrol.com.br.api.clientes.Clientes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitasRepository extends JpaRepository<Receitas, Long>{
        Page<Receitas> findAll(Pageable paginacao);
    }
