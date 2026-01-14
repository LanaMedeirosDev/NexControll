package nexcontrol.com.br.api.financeiro.cadastros.contasFinanceiras;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroContas(

        @NotBlank
        String nomeConta) {
}
