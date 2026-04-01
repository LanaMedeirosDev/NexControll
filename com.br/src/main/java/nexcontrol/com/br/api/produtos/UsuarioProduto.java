package nexcontrol.com.br.api.produtos;

import jakarta.persistence.*;
import nexcontrol.com.br.api.usuario.Usuarios;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_produto")
public class UsuarioProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produtos produto;

    private BigDecimal precoPersonalizado;

    private LocalDate dataCadastro;

    private Boolean ativo;

    public UsuarioProduto() {
    }
}
