package br.com.foodtosave.adapter.interfaces.datasource;

import br.com.foodtosave.core.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteRepository {

    Cliente create(Cliente cliente);
    Page<Cliente> findAll(Pageable page);
    Cliente getReferenceById(Long id);
    void atualizar(Cliente cliente);
    void delete(Long id);
}
