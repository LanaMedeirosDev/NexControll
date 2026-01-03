package nexcontrol.com.br.api.produtos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produtos, Long> {
    Page<Produtos> findByAtivoProdutoTrue(Pageable paginacao);
}
