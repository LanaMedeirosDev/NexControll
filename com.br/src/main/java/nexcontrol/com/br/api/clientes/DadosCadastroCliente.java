package nexcontrol.com.br.api.clientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import nexcontrol.com.br.api.endereco.dadosDoEndereco;

//Criando as Strings para o cadastro do cliente
public record DadosCadastroCliente(

        @NotBlank
        String nome,

        @NotBlank
        String cpfCnpj,

        @Pattern(regexp = "\\d{8,13}")
        String ieRg,

        String telefone,

        @Pattern(regexp = "\\d{9,17}")
        String celular,

        @NotNull
        ClienteFornecedor tipoDeCadastro,

        @NotNull
        @Valid
        dadosDoEndereco endereco ) {
}
