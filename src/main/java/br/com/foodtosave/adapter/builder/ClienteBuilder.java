package br.com.foodtosave.adapter.builder;

import br.com.foodtosave.adapter.dto.request.DadosCadastroCliente;
import br.com.foodtosave.adapter.dto.response.DadosDetalheCliente;
import br.com.foodtosave.adapter.dto.response.DadosResponseCliente;
import br.com.foodtosave.core.entity.Cliente;
import br.com.foodtosave.core.entity.Endereco;

public class ClienteBuilder {
    public static Cliente fromRequestToDomain(DadosCadastroCliente dados){
        return Cliente.builder()
                .email(dados.email())
                .nome(dados.nome())
                .endereco(
                        Endereco.builder()
                                .cep(dados.endereco().cep())
                                .uf(dados.endereco().uf())
                                .bairro(dados.endereco().bairro())
                                .cidade(dados.endereco().cidade())
                                .complemento(dados.endereco().complemento())
                                .logradouro(dados.endereco().logradouro())
                                .numero(dados.endereco().numero())
                                .build()
                )
                .build();
    }

    public static DadosResponseCliente fromDomainToResponse(Cliente cliente){
        return DadosResponseCliente.builder()
                .id(cliente.getId())
                .email(cliente.getEmail())
                .nome(cliente.getNome())
                .build();
    }

    public static DadosDetalheCliente fromDomainToDetail(Cliente cliente){
        return DadosDetalheCliente.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .endereco(cliente.getEndereco())
                .build();
    }
}
