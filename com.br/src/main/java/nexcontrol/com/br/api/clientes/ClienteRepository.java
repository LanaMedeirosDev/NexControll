package nexcontrol.com.br.api.clientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//Conexão com o banco de dados

public interface ClienteRepository extends JpaRepository<Clientes, Long> {
    Page<Clientes> findAllByAtivoTrue(Pageable paginacao);
}