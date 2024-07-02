package br.com.foodtosave.adapter.dto.response;

import lombok.Builder;

@Builder
public record DadosResponseCliente(
        Long id,
        String nome,
        String email
) {
}
