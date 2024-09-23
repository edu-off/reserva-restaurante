package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AlteraDadosReservaTest {

    @Nested
    public class ValidacaoAlteracaoDadosReserva {

        @Test
        @DisplayName("Deve alterar dados da reserva corretamente")
        public void deveAlterarDadosDaReservaCorretamente() {
            ReservaDTO reservaDTO = new ReservaDTO(null, "CANCELADA", LocalDateTime.now().plusHours(7), 1, null, null, null);
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Cliente cliente = new Cliente("test@test.com", "nome", 1, 1L);
            restaurante.setId(1L);
            cliente.setEmail("teste@teste.com.br");
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Reserva reservaOld = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restaurante, mesa, cliente);
            Reserva reservaChanged = new AlteraDadosReserva().execute(reservaOld, reservaDTO);
            assertThat(reservaChanged).isInstanceOf(Reserva.class).isNotNull();
            assertThat(reservaChanged.getStatus()).isNotEqualTo(StatusReserva.ATIVA);
        }

    }

}
