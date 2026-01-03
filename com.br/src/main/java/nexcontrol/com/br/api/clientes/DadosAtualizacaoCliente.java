package nexcontrol.com.br.api.clientes;

import jakarta.validation.constraints.NotNull;
import nexcontrol.com.br.api.endereco.dadosDoEndereco;

//Dados que eu permito que o cliente faça a alteração no cadastro
//ID not null pois deve ser necessário informar o ID do cadastro para que o banco saiba qual cadastro vai ser modificadp
public record DadosAtualizacaoCliente(

        @NotNull
        Long id,
        String nome,
        String cpfCnpj,
        String ieRg,
        String telefone,
        String celular,
        ClienteFornecedor tipoDeCadastro,
        dadosDoEndereco endereco) {
}
