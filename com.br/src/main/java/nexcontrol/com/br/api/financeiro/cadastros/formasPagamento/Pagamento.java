package nexcontrol.com.br.api.financeiro.cadastros.formasPagamento;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras.Contas;

@Table(name = "pagamento")
@Entity(name = "Pagamento")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomePagamento")
    private String nomePagamento;

    @ManyToOne
    @JoinColumn(name = "idConta")
    private Contas contas;

    private boolean ativoPagamento;

    public String getNomePagamento() {
        return nomePagamento;
    }
    public Long getId() {
        return id;
    }
    public Contas getContas() {
        return contas;
    }

    //Referenciar conta

    public Pagamento(DadosCadastroPagamento dados, Contas contas) {
        this.nomePagamento = dados.nomePagamento();
        this.contas = contas;
        this.ativoPagamento = true;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoPagamento dados, Contas contas) {
        if (dados.nomePagamento() != null) {
            this.nomePagamento = dados.nomePagamento();
        }
        if (contas != null) {
            this.contas = contas;
        }
    }

    public void excluir() {
        this.ativoPagamento = false;
    }

}
