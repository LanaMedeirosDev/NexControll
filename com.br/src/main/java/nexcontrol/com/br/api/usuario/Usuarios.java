package nexcontrol.com.br.api.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nexcontrol.com.br.api.enderecoUsuario.EnderecoUsuario;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Usuarios {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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