package br.com.reserva.application.usecases.cliente;

import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.application.exceptions.EntityNotFoundException;
import br.com.reserva.application.gateways.ClienteGateway;
import br.com.reserva.domain.entities.Cliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VerificaExistenciaClienteTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private VerificaExistenciaCliente verificaExistenciaCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoExistenciaCliente {

        @Test
        @DisplayName("Deve retornar cliente")
        public void deveRetornarCliente() {
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            when(clienteGateway.buscaClientePorId(cliente.getEmail())).thenReturn(cliente);
            Cliente clienteFinded = verificaExistenciaCliente.execute(cliente.getEmail());
            assertThat(clienteFinded).isInstanceOf(Cliente.class).isNotNull().isEqualTo(cliente);
            verify(clienteGateway, times(1)).buscaClientePorId(cliente.getEmail());
        }

        @Test
        @DisplayName("Deve lançar exceção para cliente inexistente")
        public void deveLançarExcecaoParaClienteInexistente() {
            String email = "teste@teste.com.br";
            when(clienteGateway.buscaClientePorId(email)).thenReturn(null);
            Assertions.assertThatThrownBy(() -> verificaExistenciaCliente.execute(email))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cliente não encontrado");
        }

    }

}
