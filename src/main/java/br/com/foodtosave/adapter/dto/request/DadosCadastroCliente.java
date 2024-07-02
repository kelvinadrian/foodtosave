package br.com.foodtosave.adapter.dto.request;

import br.com.foodtosave.adapter.dto.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCliente(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @Valid
        @NotNull
        DadosEndereco endereco
) {
}
