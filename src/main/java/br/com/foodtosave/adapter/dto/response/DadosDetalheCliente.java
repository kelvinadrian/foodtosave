package br.com.foodtosave.adapter.dto.response;

import br.com.foodtosave.core.entity.Endereco;
import lombok.Builder;

@Builder
public record DadosDetalheCliente(
        Long id,
        String nome,
        String email,
        Endereco endereco
) {
}
