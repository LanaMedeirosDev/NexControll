package nexcontrol.com.br.api.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioProdutoRepository  extends JpaRepository<UsuarioProduto, Long> {
    List<UsuarioProduto> findByUsuarioId(Long usuarioId);
}
