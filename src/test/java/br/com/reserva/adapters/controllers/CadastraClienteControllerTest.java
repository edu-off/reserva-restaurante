package br.com.reserva.adapters.controllers;

import br.com.reserva.adapters.presenters.CadastraClientePresenter;
import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.application.exceptions.EntityNotFoundException;
import br.com.reserva.application.usecases.cliente.SalvaCliente;
import br.com.reserva.application.usecases.cliente.ValidaCliente;
import br.com.reserva.application.usecases.cliente.VerificaExistenciaCliente;
import br.com.reserva.domain.entities.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class CadastraClienteControllerTest {

    @Mock
    private VerificaExistenciaCliente verificaExistenciaCliente;

    @Mock
    private ValidaCliente validaCliente;

    @Mock
    private SalvaCliente salvaCliente;

    @Mock
    private CadastraClientePresenter presenter;

    @InjectMocks
    private CadastraClienteController cadastraClienteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoCadastroCliente {

        @Test
        @DisplayName("Deve lancar excecao para cliente ja existente")
        public void deveLancarExcecaoParaClienteJaExistente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            when(verificaExistenciaCliente.execute(clienteDTO.getEmail())).thenReturn(cliente);
            assertThatThrownBy(() -> cadastraClienteController.execute(clienteDTO))
                    .isInstanceOf(ClienteException.class);
        }


        @Test
        @DisplayName("Deve lancar excecao na validacao de cliente")
        public void deveLancarExcecaoNaValidacaoDeCliente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            when(verificaExistenciaCliente.execute(clienteDTO.getEmail())).thenThrow(EntityNotFoundException.class);
            when(validaCliente.execute(clienteDTO)).thenThrow(ClienteException.class);
            assertThatThrownBy(() -> cadastraClienteController.execute(clienteDTO))
                    .isInstanceOf(ClienteException.class);
        }

        @Test
        @DisplayName("Deve cadastrar cliente corretamente")
        public void deveCadastrarClienteCorretamente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            when(verificaExistenciaCliente.execute(clienteDTO.getEmail())).thenThrow(EntityNotFoundException.class);
            when(validaCliente.execute(clienteDTO)).thenReturn(cliente);
            when(salvaCliente.execute(cliente)).thenReturn(cliente);
            when(presenter.execute(cliente)).thenReturn(clienteDTO);
            ClienteDTO clienteDTOCreated = cadastraClienteController.execute(clienteDTO);
            assertThat(clienteDTOCreated).isInstanceOf(ClienteDTO.class).isNotNull();
        }

    }

}
