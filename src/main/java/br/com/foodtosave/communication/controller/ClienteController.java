package br.com.foodtosave.communication.controller;

import br.com.foodtosave.adapter.builder.ClienteBuilder;
import br.com.foodtosave.adapter.dto.request.DadosAtualizarCliente;
import br.com.foodtosave.adapter.dto.request.DadosCadastroCliente;
import br.com.foodtosave.adapter.dto.response.DadosResponseCliente;
import br.com.foodtosave.adapter.interfaces.gateway.ClienteGateway;
import br.com.foodtosave.adapter.interfaces.usecase.ClienteUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController {

    final ClienteGateway clienteGateway;
    final ClienteUseCase clienteUseCase;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid DadosCadastroCliente dadosCadastroCliente, UriComponentsBuilder uriComponentsBuilder){
        var cliente = ClienteBuilder.fromRequestToDomain(dadosCadastroCliente);
        cliente = clienteUseCase.create(cliente, clienteGateway);

        var uri = uriComponentsBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).body(ClienteBuilder.fromDomainToDetail(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosResponseCliente>> findAll(@PageableDefault(sort = {"nome"}) Pageable page){
        var cliente = clienteUseCase.findAll(clienteGateway, page);

        var retorno = cliente.map(ClienteBuilder::fromDomainToResponse);

        return ResponseEntity.ok(retorno);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid DadosAtualizarCliente dados){
        var cliente = clienteUseCase.getReferenceById(dados.id(), clienteGateway);

        if(cliente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente com o ID " + dados.id() + " não encontrado.");
        }

        clienteUseCase.atualizar(cliente, dados);

        return ResponseEntity.ok(ClienteBuilder.fromDomainToDetail(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        clienteUseCase.delete(id, clienteGateway);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id) {
        var cliente = clienteUseCase.getReferenceById(id, clienteGateway);

        if(cliente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente com o ID " + id + " não encontrado.");
        }

        return ResponseEntity.ok(ClienteBuilder.fromDomainToDetail(cliente));
    }
}
