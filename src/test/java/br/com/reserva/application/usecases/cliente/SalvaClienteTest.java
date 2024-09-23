package br.com.reserva.application.usecases.cliente;

import br.com.reserva.application.gateways.ClienteGateway;
import br.com.reserva.domain.entities.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SalvaClienteTest {

    @Mock
    private ClienteGateway clienteGateway;

    @InjectMocks
    private SalvaCliente salvaCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoSalvaCliente {

        @Test
        @DisplayName("Deve salvar cliente corretamente")
        public void deveSalvarClienteCorretamente() {
            String id = UUID.randomUUID().toString();
            Cliente cliente = new Cliente("test@test.com", "nome", 1, 1L);
            Cliente clienteMocked = new Cliente("test@test.com", "nome", 1, 1L);
            when(clienteGateway.salvaCliente(cliente)).thenReturn(clienteMocked);
            Cliente clienteSaved = salvaCliente.execute(cliente);
            assertThat(clienteSaved).isInstanceOf(Cliente.class).isNotNull();
            assertThat(clienteSaved).isEqualTo(clienteMocked);
            verify(clienteGateway, times(1)).salvaCliente(cliente);
        }

    }

}
