package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.gateways.AvaliacaoGateway;
import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.domain.entities.*;
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

import static org.mockito.Mockito.*;

public class RegistraReservaTest {

    @Mock
    private ReservaGateway reservaGateway;

    @InjectMocks
    private RegistraReserva registraReserva;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    public class ValidaRegistraReserva {

        @Test
        @DisplayName("Deve registrar reserva corretamente")
        public void deveRegistrarAvaliacaoCorretamente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Cliente cliente = new Cliente("test@test.com", "nome", 1, 1L);
            restaurante.setId(1L);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            cliente.setEmail("teste@teste.com.br");
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restaurante, mesa, cliente);
            when(reservaGateway.salvaReserva(reserva)).thenReturn(reserva);
            registraReserva.execute(reserva);
            verify(reservaGateway, times(1)).salvaReserva(reserva);
        }

    }

}
