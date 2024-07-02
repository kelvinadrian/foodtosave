package br.com.foodtosave.adapter.dto.request;

import br.com.foodtosave.adapter.dto.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarCliente(
        @NotNull
        Long id,
        String nome,
        String email,
        DadosEndereco endereco
) {
}
