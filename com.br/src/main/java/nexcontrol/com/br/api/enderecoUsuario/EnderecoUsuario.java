package nexcontrol.com.br.api.enderecoUsuario;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class EnderecoUsuario {

    private String logradouroUsuario;
    private String bairroUsuario;
    private String cepUsuario;
    private String cidadeUsuario;
    private String ufUsuario;
    private String numerousuario;
    private String complementoUsuario;

    public EnderecoUsuario(dadosDoEnderecoUsuario dados) {
        this.logradouroUsuario = dados.logradouroUsuario();
        this.bairroUsuario = dados.bairroUsuario();
        this.cepUsuario = dados.cepUsuario();
        this.cidadeUsuario = dados.cidadeUsuario();
        this.ufUsuario = dados.ufUsuario();
        this.numerousuario = dados.numeroUsuario();
        this.complementoUsuario = dados.complementoUsuario();
    }
}



