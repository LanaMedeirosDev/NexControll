package nexcontrol.com.br.api.clientes;
import nexcontrol.com.br.api.endereco.DadosDoEndereco;

public record DadosDetalhamentoCliente(
        Long id,
        String nome,
        String cpfCnpj,
        String ieRg,
        String telefone,
        String celular,
        ClienteFornecedor tipoDeCadastro,
        DadosDoEndereco endereco) {

    public DadosDetalhamentoCliente(Clientes clientes){
        this(clientes.getId(),
                clientes.getNome(),
                clientes.getCpfCnpj(),
                clientes.getIeRg(),
                clientes.getTelefone(),
                clientes.getCelular(),
                clientes.getTipoDeCadastro(),
                clientes.getDadosDoEndereco());
    }
}
