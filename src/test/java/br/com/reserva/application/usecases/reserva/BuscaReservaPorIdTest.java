package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class BuscaReservaPorIdTest {

    @Mock
    private ReservaGateway reservaGateway;

    @InjectMocks
    private BuscaReservasPorId buscaReservasPorId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoBuscaReservaPorId {

        @Test
        @DisplayName("Deve buscar reservas corretamente")
        public void deveBuscarReservasCorretamente() {
            Restaurante restaurante = new Restaurante();
            restaurante.setId(1L);
            Cliente cliente = new Cliente();
            cliente.setEmail("teste@teste.com.br");
            LocalDateTime dateTime = LocalDateTime.now().plusHours(7);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, dateTime, 2, restaurante, mesa, cliente);
            when(reservaGateway.buscaReservaPorId(1L)).thenReturn(reserva);
            Reserva reservasReturned = buscaReservasPorId.execute(1L);
            assertThat(reservasReturned).isInstanceOf(Reserva.class).isNotNull();
        }

        @Test
        @DisplayName("Deve lancar excecao ao buscar reserva")
        public void deveLancarExcecaoAoBuscarReserva() {
            when(reservaGateway.buscaReservaPorId(1L)).thenReturn(null);
            assertThatThrownBy(() -> buscaReservasPorId.execute(1L))
                    .isInstanceOf(ReservaException.class);
        }


    }

}
