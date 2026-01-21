package nexcontrol.com.br.api.produtos;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nexcontrol.com.br.api.usuario.Usuarios;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "produtos")
@Entity(name = "Produto")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Produtos {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "produto")
    private List<UsuarioProduto> usuarios;

    private String nomeDoProduto;

    @Column(name = "ncm")
    private String ncm;

    @Column(name = "valorDeVenda")
    private BigDecimal valorDeVenda;

    @Column(name = "valorDeCusto")
    private BigDecimal valorDeCusto;

    @Column(name = "unidadeDeMedida")
    private String unidadeDeMedida;

    @Column(name = "codigoSku")
    private String codigoSku;

    @Column(name = "codigoDeBarras")
    private String codigoDeBarras;

    @Column(name = "cfopPreferencial", length = 6)
    private String cfopPreferencial;

    @Column(name = "tipoDeCadastroProduto")
    @Enumerated(EnumType.STRING)
    private ProdutoServico tipoDeCadastroProduto;


    public String getNomeDoProduto(){
        return nomeDoProduto;
    }
    public String getValorDeVenda(){
        return valorDeVenda;
    }
    public String getUnidadeDeMedida(){
        return unidadeDeMedida;
    }
    public ProdutoServico getTipoDeCadastroProduto(){
        return tipoDeCadastroProduto;
    }
    public String getNcm() {     //add
        return ncm;
    }
    public String getValorDeCusto(){
        return valorDeCusto;
    }
    public String getCodigoSku(){
        return codigoSku;
    }
    public String getCfopPreferencial(){
        return cfopPreferencial;
    }
    public String getCodigoDeBarras(){
        return codigoDeBarras;
    }

    public Long getIdProduto(){
        return id;
    }

    private boolean ativoProduto;

    public Produtos(DadosCadastroProdutos dados) {
        this.nomeDoProduto = dados.nomeDoProduto();
        this.ncm = dados.ncm();
        this.valorDeVenda = dados.valorDeVenda();
        this.valorDeCusto = dados.valorDeCusto();
        this.unidadeDeMedida = dados.unidadeDeMedida();
        this.tipoDeCadastroProduto = dados.tipoDeCadastroProduto();
        this.codigoSku = dados.codigoSku();
        this.codigoDeBarras = dados.codigoDeBarras();
        this.cfopPreferencial = dados.cfopPreferencial();
        this.ativoProduto = true;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoProduto dados) {
        if (dados.nomeDoProduto() != null) {
            this.nomeDoProduto = dados.nomeDoProduto();
        }
        if (dados.ncm() != null) {
            this.ncm = dados.ncm();
        }
        if (dados.valorDeVenda() != null) {
            this.valorDeVenda = dados.valorDeVenda();
        }
        if (dados.valorDeCusto() != null) {
            this.valorDeCusto = dados.valorDeCusto();
        }
        if (dados.unidadeDeMedida() != null) {
            this.unidadeDeMedida = dados.unidadeDeMedida();
        }
        if (dados.tipoDeCadastroProduto() != null) {
            this.tipoDeCadastroProduto = dados.tipoDeCadastroProduto();
        }
        if (dados.codigoSku() != null) {
            this.codigoSku = dados.codigoSku();
        }
        if (dados.cfopPreferencial() != null) {
            this.cfopPreferencial = dados.cfopPreferencial();
        }
        if (dados.codigoDeBarras() != null) {
            this.codigoDeBarras = dados.codigoDeBarras();
        }
    }

    public void excluir() {
        this.ativoProduto = false;
    }
}
