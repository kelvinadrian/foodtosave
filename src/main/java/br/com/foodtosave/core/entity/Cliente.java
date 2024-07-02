package br.com.foodtosave.core.entity;

import br.com.foodtosave.adapter.dto.request.DadosAtualizarCliente;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "cliente")
@Entity(name= "Cliente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    String email;

    @Embedded
    Endereco endereco;

    public void atualizarInformacoes(DadosAtualizarCliente dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }

    }
}
