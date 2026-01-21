package nexcontrol.com.br.api.clientes;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//List faz a verificação apenas de clientes ativos duarante a consulta por clientes
public interface UsuarioClienteRepository extends JpaRepository<UsuarioCliente, Long> {
    List<UsuarioCliente> findByUsuarioId(Long usuarioId);
}
