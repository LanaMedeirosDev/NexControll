package nexcontrol.com.br.api.enderecoUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record dadosDoEnderecoUsuario(

        @NotBlank
        String logradouroUsuario,

        @NotBlank
        String bairroUsuario,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cepUsuario,

        @NotBlank
        String cidadeUsuario,

        @NotBlank
        String ufUsuario,

        @NotBlank
        String numeroUsuario,

        String complementoUsuario ) {
}
