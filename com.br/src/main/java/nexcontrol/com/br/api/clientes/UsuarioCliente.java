package nexcontrol.com.br.api.clientes;

import jakarta.persistence.*;
import nexcontrol.com.br.api.usuario.Usuarios;
import java.time.LocalDate;

//Permite que o cliente se relacione com o usuário de forma personalizada, então mesmo com o mesmo cadastro eles interagem de forma diferente com cada usuário
@Entity
@Table(name = "usuario_cliente")
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

    public UsuarioCliente() {
    }
}
