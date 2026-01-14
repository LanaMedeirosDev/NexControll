package nexcontrol.com.br.api.financeiro.cadastros.formasPagamento;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroPagamento(

        @NotBlank
        String nomePagamento,

        Long idConta){
}
