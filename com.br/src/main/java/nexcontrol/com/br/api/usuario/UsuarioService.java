package nexcontrol.com.br.api.usuario;

import nexcontrol.com.br.api.enderecoUsuario.EnderecoUsuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(DadosCadastroUsuario dados) {

        // Por enquanto, senha salva como está (plain text)
        String senha = dados.senha();

        Usuarios usuario = new Usuarios(
                dados.nomeUsuario(),
                dados.email(),
                dados.senha());

        repository.save(usuario);
    }
}