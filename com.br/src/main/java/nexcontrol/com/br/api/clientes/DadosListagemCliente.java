package nexcontrol.com.br.api.clientes;

//Dados que eu gostaria de limitar para que apareçam no front na hora da listagem e paginação
public record DadosListagemCliente(
        String nome,
        String cpfCnpj,
        ClienteFornecedor tipoDeCadastro,
        String telefone,
        String ieRg,
        String celular,
        Long id
) {
    public DadosListagemCliente(Clientes cliente) {
        this(
                cliente.getNome(),
                cliente.getCpfCnpj(),
                cliente.getTipoDeCadastro(),
                cliente.getTelefone(),
                cliente.getIeRg(),
                cliente.getCelular(),
                cliente.getId()
        );
    }
}
