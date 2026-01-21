package nexcontrol.com.br.api.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nexcontrol.com.br.api.clientes.Clientes;
import nexcontrol.com.br.api.clientes.UsuarioCliente;
import nexcontrol.com.br.api.enderecoUsuario.EnderecoUsuario;
import nexcontrol.com.br.api.produtos.Produtos;
import nexcontrol.com.br.api.produtos.UsuarioProduto;

import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Usuarios {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "usuario")
    private List<UsuarioCliente> clientes;

    @OneToMany(mappedBy = "usuario")
    private List<UsuarioProduto> produtos;

    private String nomeUsuario;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "celularDoUsuario")
    private String celularDoUsuario;

    @Embedded
    private EnderecoUsuario enderecoUsuario;

    public Usuarios(DadosCadastroUsuario dados) {
        this.nomeUsuario = dados.nomeUsuario();
        this.email = dados.email();
        this.celularDoUsuario = dados.celularDoUsuario();
        this.enderecoUsuario = new EnderecoUsuario(dados.enderecoUsuario());
    }
}