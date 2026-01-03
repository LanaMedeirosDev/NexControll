package nexcontrol.com.br.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nexcontrol.com.br.api.usuario.DadosCadastroUsuario;
import nexcontrol.com.br.api.usuario.UsuarioRepository;
import nexcontrol.com.br.api.usuario.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuariosController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) { repository.save(new Usuarios(dados));}
}
