package nexcontrol.com.br.api.clientes;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nexcontrol.com.br.api.endereco.Endereco;

@Table(name = "clientes")
@Entity(name = "Cliente")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

//Criação das Strings utilizadas para preencher os campos do cadastro
public class Clientes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Column(name = "cpfCnpj")
    private String cpfCnpj;

    @Column(name = "ieRg")
    private String ieRg;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "celular")
    private String celular;

    @Column(name = "tipoDeCadastro")
    @Enumerated(EnumType.STRING)
    private ClienteFornecedor tipoDeCadastro;

    //Criando os Getters de forma manual já que não utilizei o Lombok
    public String getNome(){
        return nome;
    }
    public String getCpfCnpj(){
        return cpfCnpj;
    }
    public ClienteFornecedor getTipoDeCadastro(){
        return tipoDeCadastro;
    }
    public Long geiId(){
        return id;
    }

    @Embedded
    private Endereco endereco;

    //Boolean para informar ao banco se o cadastro é ativo ou não
    private boolean ativo;

    public Clientes(DadosCadastroCliente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.cpfCnpj = dados.cpfCnpj();
        this.ieRg = dados.ieRg();
        this.telefone = dados.telefone();
        this.celular = dados.celular();
        this.tipoDeCadastro = dados.tipoDeCadastro();
        this.endereco = new Endereco(dados.endereco());
    }

    //Os IF permitem que o usuário modifique o campo que ele quiser, mas se ele deixar de modificar algum o cadastro ele vai se manter da mesma forma que estava
    public void atualizarInformacoes(@Valid DadosAtualizacaoCliente dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.cpfCnpj() != null) {
            this.cpfCnpj = dados.cpfCnpj();
        }
        if (dados.ieRg() != null) {
            this.ieRg = dados.ieRg();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.celular() != null) {
            this.celular = dados.celular();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco()) ;
        }

    }

    //Assim que excluir altera o boolean de 1(true) para o false, simbolizando que o cliente está inativo
    public void excluir() {
        this.ativo = false;
    }
}

