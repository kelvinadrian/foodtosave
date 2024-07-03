package br.com.foodtosave.core.usercase;

import br.com.foodtosave.adapter.dto.request.DadosAtualizarCliente;
import br.com.foodtosave.adapter.interfaces.gateway.ClienteGateway;
import br.com.foodtosave.adapter.interfaces.usecase.ClienteUseCase;
import br.com.foodtosave.adapter.redis.RedisConfig;
import br.com.foodtosave.core.entity.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteUseCaseImpl implements ClienteUseCase {

    @Override
    public Cliente create(Cliente cliente, ClienteGateway clienteGateway) {
        return clienteGateway.create(cliente);
    }

    @Override
    public Page<Cliente> findAll(ClienteGateway clienteGateway, Pageable page) {
        return clienteGateway.findAll(page);
    }

    @Override
    public Cliente getReferenceById(Long id, ClienteGateway clienteGateway) {
        return clienteGateway.getReferenceById(id);
    }

    @Override
    public void atualizar(Cliente cliente, DadosAtualizarCliente dados) {
        cliente.atualizarInformacoes(dados);
    }

    @Override
    public void delete(Long id, ClienteGateway clienteGateway) {
        clienteGateway.delete(id);
    }
}
