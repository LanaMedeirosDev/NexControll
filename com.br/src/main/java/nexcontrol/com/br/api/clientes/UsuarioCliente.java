package nexcontrol.com.br.api.clientes;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import nexcontrol.com.br.api.usuario.Usuarios;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;

//Permite que o cliente se relacione com o usuário de forma personalizada, então mesmo com o mesmo cadastro eles interagem de forma diferente com cada usuário
public class UsuarioCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes cliente;

    private LocalDate dataCadastro;

    private Boolean ativo;
}
