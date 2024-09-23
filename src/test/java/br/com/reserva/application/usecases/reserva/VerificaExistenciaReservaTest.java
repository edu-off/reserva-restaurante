package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.exceptions.EntityNotFoundException;
import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.assertj.core.api.Assertions;
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

public class VerificaExistenciaReservaTest {

    @Mock
    private ReservaGateway reservaGateway;

    @InjectMocks
    private VerificaExistenciaReserva verificaExistenciaReserva;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidacaoExistenciaReserva {

        @Test
        @DisplayName("Deve retornar reserva")
        public void deveRetornarReserva() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setId(1L);
            Cliente cliente = new Cliente("teste@steste.com.br", "nome", 1, 1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 1, restaurante, mesa, cliente);
            reserva.setId(1L);
            when(reservaGateway.buscaReservaPorId(reserva.getId())).thenReturn(reserva);
            Reserva reservaFinded = verificaExistenciaReserva.execute(reserva.getId());
            assertThat(reservaFinded).isInstanceOf(Reserva.class).isNotNull().isEqualTo(reserva);
            verify(reservaGateway, times(1)).buscaReservaPorId(reserva.getId());
        }

        @Test
        @DisplayName("Deve lançar exceção para restaurante inexistente")
        public void deveLançarExcecaoParaRestauranteInexistente() {
            Long id = 1L;
            when(reservaGateway.buscaReservaPorId(id)).thenReturn(null);
            Assertions.assertThatThrownBy(() -> verificaExistenciaReserva.execute(id))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("reserva não encontrada");
        }

    }

}
