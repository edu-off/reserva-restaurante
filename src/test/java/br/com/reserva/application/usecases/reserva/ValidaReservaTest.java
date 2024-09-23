package br.com.reserva.application.usecases.reserva;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import br.com.reserva.domain.objectsvalue.Endereco;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidaReservaTest {

    private final ValidaReserva validaReserva = new ValidaReserva();

    @Nested
    public class ValidacaoTransformacaoDtoParaDominio {

        @Test
        @DisplayName("Deve lançar exceção para status de reserva invalido")
        public void deveLancarExcecaoParaStatusDeReservaInvalido() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            Cliente cliente = new Cliente("test@test.com", "nome", 1, 1L);
            ReservaDTO reservaDTO = new ReservaDTO(null, "XXXXX", null, 1, null, null, null);
            Assertions.assertThatThrownBy(() -> validaReserva.execute(reservaDTO, restaurante, mesa, cliente))
                    .isInstanceOf(ReservaException.class)
                    .hasMessage("erro ao registrar reserva: status inválido");
        }


        @Test
        @DisplayName("Deve lançar exceção para reserva com algum dado invalido")
        public void deveLancarExcecaoParaReservaComAlgumDadoInvalido() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            Cliente cliente = new Cliente("test@test.com", "nome", 1, 1L);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", null, 1, null, null, null);
            Assertions.assertThatThrownBy(() -> validaReserva.execute(reservaDTO, restaurante, mesa, cliente))
                    .isInstanceOf(ReservaException.class)
                    .hasMessage("erro ao registrar reserva: horário de agendamento inválido");
        }

        @Test
        @DisplayName("Deve retornar endereço corretamente")
        public void deveRetornarEnderecoCorretamente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            mesa.setId(1L);
            Cliente cliente = new Cliente("test@test.com", "nome", 1, 1L);
            restaurante.setId(1L);
            cliente.setEmail("teste@teste.com.br");
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, null, null, null);
            Reserva reserva = validaReserva.execute(reservaDTO, restaurante, mesa, cliente);
            assertThat(reserva).isInstanceOf(Reserva.class).isNotNull();
            assertThat(reserva.getId()).isNull();
            assertThat(reserva.getStatus()).isEqualTo(StatusReserva.ATIVA);
            assertThat(reserva.getAgendamento()).isInstanceOf(LocalDateTime.class).isNotNull();
            assertThat(reserva.getQuantidadePessoas()).isEqualTo(1);
            assertThat(reserva.getRestaurante()).isInstanceOf(Restaurante.class).isEqualTo(restaurante);
            assertThat(reserva.getCliente()).isInstanceOf(Cliente.class).isEqualTo(cliente);
            assertThat(reserva.getAvaliacao()).isNull();
        }

    }

}
