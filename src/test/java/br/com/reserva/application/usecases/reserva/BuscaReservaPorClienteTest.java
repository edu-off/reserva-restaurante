package br.com.reserva.application.usecases.reserva;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaReservaPorClienteTest {

    @Mock
    private ReservaGateway reservaGateway;

    @InjectMocks
    private BuscaReservasPorCliente buscaReservasPorCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoBuscaReservaPorClientePeriodo {

        @Test
        @DisplayName("Deve buscar reservas corretamente")
        public void deveBuscarReservasCorretamente() {
            String email = "teste@teste.com.br";
            Pageable pageable = PageRequest.of(0, 10);
            Restaurante restaurante = new Restaurante();
            restaurante.setId(1L);
            Cliente cliente = new Cliente();
            cliente.setEmail(email);
            LocalDateTime dateTime = LocalDateTime.now().plusHours(7);
            Mesa mesa1 = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa1.setId(1L);
            Mesa mesa2 = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa2.setId(2L);
            Mesa mesa3 = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa3.setId(3L);
            Reserva reserva1 = new Reserva(StatusReserva.ATIVA, dateTime, 2, restaurante, mesa1, cliente);
            Reserva reserva2 = new Reserva(StatusReserva.ATIVA, dateTime, 2, restaurante, mesa2, cliente);
            Reserva reserva3 = new Reserva(StatusReserva.ATIVA, dateTime, 2, restaurante, mesa3, cliente);
            List<Reserva> reservas = List.of(reserva1, reserva2, reserva3);
            Page<Reserva> reservasMocked = PageableExecutionUtils.getPage(reservas, pageable, reservas::size);
            when(reservaGateway.buscaReservasPorCliente(email, pageable)).thenReturn(reservasMocked);
            Page<Reserva> reservasReturned = buscaReservasPorCliente.execute(email, pageable);
            assertThat(reservasReturned).isInstanceOf(Page.class).isNotEmpty().isEqualTo(reservasMocked);
        }

    }

}
