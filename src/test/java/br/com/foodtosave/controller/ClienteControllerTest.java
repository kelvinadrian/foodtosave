package br.com.foodtosave.controller;

import br.com.foodtosave.adapter.dto.DadosEndereco;
import br.com.foodtosave.adapter.dto.request.DadosAtualizarCliente;
import br.com.foodtosave.adapter.dto.request.DadosCadastroCliente;
import br.com.foodtosave.adapter.dto.response.DadosResponseCliente;
import br.com.foodtosave.adapter.interfaces.usecase.ClienteUseCase;
import br.com.foodtosave.communication.controller.ClienteController;
import br.com.foodtosave.core.entity.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteUseCase clienteUseCase;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void testCreate() {
        // Mockando o comportamento do clienteUseCase.create
        when(clienteUseCase.create(any(), any())).thenReturn(new Cliente());

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(null, "teste", new DadosEndereco("123","123","123","123","123","","123"));

        ResponseEntity responseEntity = clienteController.create(dadosCadastroCliente, uriComponentsBuilder);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        // Adicione mais asserts conforme necessário para verificar o comportamento esperado
    }

    @Test
    void testCreateWithValidationError() throws Exception {
        // Dados inválidos que devem falhar na validação
        DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(null, "teste", new DadosEndereco("123", "123", "123", "123", "123", "", "123"));

        // Converte o objeto para JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(dadosCadastroCliente);

        // Realiza a chamada POST para o endpoint /cliente
        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindAll() {
        Page<DadosResponseCliente> pageClientes = new PageImpl<>(Collections.emptyList());

        // Mockando o comportamento do clienteUseCase.findAll
        when(clienteUseCase.findAll(any(), any())).thenAnswer(invocation -> {
            Pageable pageable = invocation.getArgument(1, Pageable.class);
            return pageClientes;
        });

        Pageable pageable = Pageable.unpaged();
        ResponseEntity<Page<DadosResponseCliente>> responseEntity = clienteController.findAll(pageable);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Adicione mais asserts conforme necessário para verificar o comportamento esperado
    }


    @Test
    void testUpdate() {
        // Mockando o comportamento do clienteUseCase.getReferenceById
        when(clienteUseCase.getReferenceById(anyLong(), any())).thenReturn(new Cliente());

        // Mockando o comportamento do clienteUseCase.atualizar
        doNothing().when(clienteUseCase).atualizar(any(), any());

        DadosAtualizarCliente dadosAtualizarCliente = new DadosAtualizarCliente(1L, "teste","",null);
        ResponseEntity responseEntity = clienteController.update(dadosAtualizarCliente);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Adicione mais asserts conforme necessário para verificar o comportamento esperado
    }

    @Test
    void testUpdateWithNonExistingId() {
        // Mockando o comportamento do clienteUseCase.getReferenceById para retornar Optional.empty()
        when(clienteUseCase.getReferenceById(anyLong(), any())).thenReturn(null);
        DadosAtualizarCliente dadosAtualizarCliente = new DadosAtualizarCliente(1L, "teste","",null);
        ResponseEntity responseEntity = clienteController.update(dadosAtualizarCliente);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testDelete() {
        // Mockando o comportamento do clienteUseCase.delete
        doNothing().when(clienteUseCase).delete(anyLong(), any());

        ResponseEntity responseEntity = clienteController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        // Adicione mais asserts conforme necessário para verificar o comportamento esperado
    }

    @Test
    void testDetail() {
        // Mockando o comportamento do clienteUseCase.getReferenceById
        when(clienteUseCase.getReferenceById(anyLong(), any())).thenReturn(new Cliente());

        ResponseEntity responseEntity = clienteController.detail(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Adicione mais asserts conforme necessário para verificar o comportamento esperado
    }

    @Test
    void testDetailWithNonExistingId() {
        // Mockando o comportamento do clienteUseCase.getReferenceById para retornar Optional.empty()
        when(clienteUseCase.getReferenceById(anyLong(), any())).thenReturn(null);

        ResponseEntity responseEntity = clienteController.detail(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        // Adicione mais asserts conforme necessário para verificar o comportamento esperado
    }
}
