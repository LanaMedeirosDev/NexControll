package nexcontrol.com.br.api.financeiro.cadastros.categoriaFinanceira;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "categoria")
@Entity(name = "Categoria")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomeCategoria")
    private String nomeCategoria;

    @Column(name = "nomeSubCategoria")
    private String nomeSubCategoria;

    private boolean ativoCategoria;

    public String getNomeCategoria() {
            return nomeCategoria;
    }
    public String getNomeSubCategoria() {
        return nomeSubCategoria;
    }
    public Long getId() {
        return id;
    }

    public Categoria(DadosCadastroCategoria dados) {
        this.nomeCategoria = dados.nomeCategoria();
        this.nomeSubCategoria = dados.nomeSubCategoria();
        this.ativoCategoria = true;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoCategoria dados) {
        if (dados.nomeCategoria() != null) {
            this.nomeCategoria = dados.nomeCategoria();
        }
        if (dados.nomeSubCategoria() != null){
            this.nomeSubCategoria = dados.nomeSubCategoria();
        }
    }

    public void excluir() {
        this.ativoCategoria = false;
    }
}
