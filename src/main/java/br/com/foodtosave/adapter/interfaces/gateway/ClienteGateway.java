package br.com.foodtosave.adapter.interfaces.gateway;

import br.com.foodtosave.core.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteGateway {

    Cliente create(Cliente cliente);
    Page<Cliente> findAll(Pageable page);

    Cliente getReferenceById(Long id);

    void delete(Long id);
}
