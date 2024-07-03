package br.com.foodtosave.communication.gateway;

import br.com.foodtosave.adapter.interfaces.datasource.ClienteRepository;
import br.com.foodtosave.adapter.interfaces.gateway.ClienteGateway;
import br.com.foodtosave.core.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ClienteGatewayImpl implements ClienteGateway {

    private final ClienteRepository clienteRepository;

    public ClienteGatewayImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Cliente create(Cliente cliente) {
        return clienteRepository.create(cliente);
    }

    @Override
    public Page<Cliente> findAll(Pageable page) {
        return clienteRepository.findAll(page);
    }

    @Override
    public Cliente getReferenceById(Long id) {
        return clienteRepository.getReferenceById(id);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.delete(id);
    }
}
