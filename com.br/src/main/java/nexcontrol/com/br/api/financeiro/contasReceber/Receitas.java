package nexcontrol.com.br.api.financeiro.contasReceber;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nexcontrol.com.br.api.clientes.Clientes;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "Receitas")
@Entity(name = "Receitas")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Receitas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valorReceita")
    private BigDecimal valorReceita;

    @Column(name = "dataVencimento")
    private LocalDate dataVencimento;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusReceita statusReceita;

    @Column(name = "dataRecebimento")
    private LocalDate dataRecebimento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes cliente;

    public Long getId() {
        return id;
    }

    public BigDecimal getValorReceita() {
        return valorReceita;
    }
    public LocalDate getDataVencimento() {
        return dataVencimento;
    }
    public String getDescricao() {
        return descricao;
    }
    public StatusReceita getStatusReceita(){
        return statusReceita;
    }
    public Clientes getCliente() {
        return cliente;
    }

    public Receitas(DadosCadastroReceitas dados, Clientes cliente) {
        this.descricao = dados.descricao();
        this.dataVencimento = dados.dataVencimento();
        this.valorReceita = dados.valorReceita();
        this.statusReceita = StatusReceita.PREVISTA;
        this.cliente = cliente;
    }

    public void atualizar(DadosAtualizacaoReceitas dados, Clientes cliente) {
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if (dados.valorReceita() != null) {
            this.valorReceita = dados.valorReceita();
        }
        if (dados.dataVencimento() != null) {
            this.dataVencimento = dados.dataVencimento();
        }
        if (cliente != null) {
            this.cliente = cliente;
        }
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoReceitas dados) {
        if (dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if (dados.valorReceita() != null) {
            this.valorReceita = dados.valorReceita();
        }
        if (dados.dataVencimento() != null) {
            this.dataVencimento = dados.dataVencimento();
        }
        if (dados.statusReceita() != null) {
            this.statusReceita = dados.statusReceita();
        }
    }

    //Para permitir marcar como recebida
    public void receber(LocalDate dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
        this.statusReceita = StatusReceita.RECEBIDA;
    }

    public void verificarAtraso() {
        if (this.statusReceita == StatusReceita.PREVISTA &&
                this.dataVencimento.isBefore(LocalDate.now())) {

            this.statusReceita = StatusReceita.ATRASADA;
        }
    }
}
