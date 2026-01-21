package nexcontrol.com.br.api.produtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import nexcontrol.com.br.api.usuario.Usuarios;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

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
}
