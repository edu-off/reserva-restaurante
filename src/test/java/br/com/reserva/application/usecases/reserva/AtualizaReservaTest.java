package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AtualizaReservaTest {

    @Mock
    private ReservaGateway reservaGateway;

    @InjectMocks
    private AtualizaReserva atualizaReserva;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoSalvaCliente {

        @Test
        @DisplayName("Deve atualizar reserva corretamente")
        public void deveAtualizarReservaCorretamente() {
            Long reservaId = 1L;
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setId(1L);
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restaurante, mesa, cliente);
            Reserva reservaMocked = new Reserva(StatusReserva.FINALIZADA, LocalDateTime.now().plusHours(7), 2, restaurante, mesa, cliente);
            when(reservaGateway.atualizaReserva(reservaId, reserva)).thenReturn(reservaMocked);
            Reserva reservaUpdated = atualizaReserva.execute(reservaId, reserva);
            assertThat(reservaUpdated).isInstanceOf(Reserva.class).isNotNull();
            assertThat(reservaUpdated).isEqualTo(reservaMocked);
            assertThat(reservaUpdated.getStatus()).isNotEqualTo(reserva.getStatus());
            verify(reservaGateway, times(1)).atualizaReserva(reservaId, reserva);
        }

    }

}
