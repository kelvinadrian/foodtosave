package br.com.foodtosave.adapter.interfaces.usecase;

import br.com.foodtosave.adapter.dto.request.DadosAtualizarCliente;
import br.com.foodtosave.adapter.interfaces.gateway.ClienteGateway;
import br.com.foodtosave.core.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteUseCase {

    Cliente create(Cliente cliente, ClienteGateway clienteGateway);
    Page<Cliente> findAll(ClienteGateway clienteGateway, Pageable page);

    Cliente getReferenceById(Long id, ClienteGateway clienteGateway);

    void atualizar(Cliente cliente, DadosAtualizarCliente dados);

    void delete(Long id, ClienteGateway clienteGateway);
}
