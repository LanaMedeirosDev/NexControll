package nexcontrol.com.br.api.usuario;

import jakarta.persistence.*;
import lombok.*;
import nexcontrol.com.br.api.clientes.UsuarioCliente;
import nexcontrol.com.br.api.enderecoUsuario.EnderecoUsuario;
import nexcontrol.com.br.api.produtos.UsuarioProduto;

import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamentos com outras entidades
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioCliente> clientes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioProduto> produtos;

    private String nomeUsuario;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    // Construtor personalizado sem id e listas
    public Usuarios(String nomeUsuario, String email, String senha) {
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
    }

    // ----------- Para o futuro: Perfis/roles -----------

    // Mantido como comentário para ativar no futuro
    /*
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Perfil> perfis;

    @Entity
    @Table(name = "perfis")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of = "id")
    public static class Perfil {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nome;
    }
    */
}