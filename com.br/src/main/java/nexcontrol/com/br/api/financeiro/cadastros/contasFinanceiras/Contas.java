package nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "contas")
@Entity(name = "Contas")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomeConta")
    private String nomeConta;

    private boolean ativoConta;

    public String getNomeConta() {
        return nomeConta;
    }
    public Long getId() {
        return id;
    }

    public Contas(DadosCadastroContas dados) {
        this.nomeConta = dados.nomeConta();
        this.ativoConta = true;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoContas dados) {
        if (dados.nomeConta() != null) {
            this.nomeConta = dados.nomeConta();
        }
    }

    public void excluir() {
        this.ativoConta = false;
    }
}
