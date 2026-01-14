package nexcontrol.com.br.api.financeiro.cadastros.categoriaFinanceira;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Page<Categoria> findAllByAtivoCategoriaTrue(Pageable paginacao);
}
