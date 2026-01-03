package nexcontrol.com.br.api.usuario;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import nexcontrol.com.br.api.enderecoUsuario.dadosDoEnderecoUsuario;

public record DadosCadastroUsuario(
        @NotBlank
        String nomeUsuario,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha,

        @Pattern(regexp = "\\d{9,17}")
        String celularDoUsuario,

        @NotNull
        @Valid
        dadosDoEnderecoUsuario enderecoUsuario) {
}
