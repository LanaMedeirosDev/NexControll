package nexcontrol.com.br.api.financeiro.contasPagar;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nexcontrol.com.br.api.clientes.Clientes;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "Despesas")
@Entity(name = "Despesas")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Despesas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricaoDespesa")
    private String descricaoDespesa;

    @Column(name = "valorDespesa")
    private BigDecimal valorDespesa;

    @Column(name = "dataVencimentoDespesa")
    private LocalDate dataVencimentoDespesa;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusDespesa statusDespesa;

    @Column(name = "dataPagamento")
    private LocalDate dataPagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes cliente;

    public Long getId() {
        return id;
    }

    public BigDecimal getValorDespesa() {
        return valorDespesa;
    }
    public LocalDate getDataVencimentoDespesa() {
        return dataVencimentoDespesa;
    }
    public String getDescricaoDespesa() {
        return descricaoDespesa;
    }
    public StatusDespesa getStatusDespesa(){
        return statusDespesa;
    }
    public Clientes getCliente() {
        return cliente;
    }

    public Despesas(DadosCadastroDespesas dados, Clientes cliente) {
        this.descricaoDespesa = dados.descricaoDespesa();
        this.dataVencimentoDespesa = dados.dataVencimentoDespesa();
        this.valorDespesa = dados.valorDespesa();
        this.statusDespesa = StatusDespesa.PREVISTA;
        this.cliente = cliente;
    }

    public void atualizar(DadosAtualizacaoDespesas dados, Clientes cliente) {
        if (dados.descricaoDespesa() != null) {
            this.descricaoDespesa = dados.descricaoDespesa();
        }
        if (dados.valorDespesa() != null) {
            this.valorDespesa = dados.valorDespesa();
        }
        if (dados.dataVencimentoDespesa() != null) {
            this.dataVencimentoDespesa = dados.dataVencimentoDespesa();
        }
        if (cliente != null) {
            this.cliente = cliente;
        }
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoDespesas dados) {
        if (dados.descricaoDespesa() != null) {
            this.descricaoDespesa = dados.descricaoDespesa();
        }
        if (dados.valorDespesa() != null) {
            this.valorDespesa = dados.valorDespesa();
        }
        if (dados.dataVencimentoDespesa() != null) {
            this.dataVencimentoDespesa = dados.dataVencimentoDespesa();
        }
        if (dados.statusDespesa() != null) {
            this.statusDespesa = dados.statusDespesa();
        }
    }

    public void pagar(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
        this.statusDespesa = StatusDespesa.PAGA;
    }

    public void verificarAtraso() {
        if (this.statusDespesa == StatusDespesa.PREVISTA &&
                this.dataVencimentoDespesa.isBefore(LocalDate.now())) {

            this.statusDespesa = StatusDespesa.ATRASADA;
        }
    }

}
