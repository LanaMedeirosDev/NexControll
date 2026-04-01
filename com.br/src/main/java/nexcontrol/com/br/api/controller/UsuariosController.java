package nexcontrol.com.br.api.controller;


import nexcontrol.com.br.api.usuario.DadosCadastroUsuario;
import nexcontrol.com.br.api.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroUsuario dados) {
        service.cadastrar(dados);
    }
}

