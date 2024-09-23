package br.com.reserva.domain.entities;

import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservaTest {

    private final StatusReserva status = StatusReserva.ATIVA;
    private final Integer quantidadePessoas = 10;
    private final Restaurante restaurante = new Restaurante();
    private final Mesa mesa = new Mesa();
    private final Cliente cliente = new Cliente();
    private final LocalDateTime agendamento = LocalDateTime.now().plusDays(1);

    @BeforeEach
    public void setup() {
        restaurante.setId(1L);
        mesa.setId(1L);
        cliente.setEmail("teste@teste.com.br");
    }

    @Nested
    public class ValidacaoConstrutor {

        @Test
        @DisplayName("Deve instanciar Reserva corretamente")
        public void deveInstanciarReservaCorretamente() {
            Reserva reserva = new Reserva(status, agendamento, quantidadePessoas, restaurante, mesa, cliente);
            assertThat(reserva).isInstanceOf(Reserva.class);
            assertThat(reserva.getId()).isNull();
            assertThat(reserva.getStatus()).isInstanceOf(StatusReserva.class).isEqualTo(status);
            assertThat(reserva.getAgendamento()).isInstanceOf(LocalDateTime.class).isEqualTo(agendamento);
            assertThat(reserva.getQuantidadePessoas()).isEqualTo(quantidadePessoas);
            assertThat(reserva.getRestaurante()).isInstanceOf(Restaurante.class).isEqualTo(restaurante);
            assertThat(reserva.getRestaurante().getId()).isEqualTo(1L);
            assertThat(reserva.getCliente()).isInstanceOf(Cliente.class).isEqualTo(cliente);
            assertThat(reserva.getCliente().getEmail()).isEqualTo("teste@teste.com.br");
            assertThat(reserva.getAvaliacao()).isNull();
        }

    }

    @Nested
    public class ValidacaoStatus {

        @Test
        @DisplayName("Deve lançar exceção para status nulo")
        public void deveLancarExcecaoParaStatusNulo() {
            StatusReserva statusNulo = null;
            assertThatThrownBy(() -> new Reserva(statusNulo, agendamento, quantidadePessoas, restaurante, mesa, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("status inválido");
        }

    }

    @Nested
    public class ValidacaoAgendamento {

        @Test
        @DisplayName("Deve lançar exceção para agendamento nulo")
        public void deveLancarExcecaoParaAgendamentoNulo() {
            LocalDateTime agendamentoNulo = null;
            assertThatThrownBy(() -> new Reserva(status, agendamentoNulo, quantidadePessoas, restaurante, mesa, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("horário de agendamento inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para agendamento inferior ao permitido")
        public void deveLancarExcecaoParaAgendamentoInferiorAoPermitido() {
            LocalDateTime agendamentoInferiorAoPermitido = LocalDateTime.now();
            assertThatThrownBy(() -> new Reserva(status, agendamentoInferiorAoPermitido, quantidadePessoas, restaurante, mesa, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("data e hora de agendamento não deve possuir intervalo inferior 6 horas do instante da reserva");
        }

        @Test
        @DisplayName("Deve lançar exceção para agendamento superior ao permitido")
        public void deveLancarExcecaoParaAgendamentoSuperiorAoPermitido() {
            LocalDateTime agendamentoSuperiorAoPermitido = LocalDateTime.now().plusMonths(4);
            assertThatThrownBy(() -> new Reserva(status, agendamentoSuperiorAoPermitido, quantidadePessoas, restaurante, mesa, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("data e hora de agendamento não deve ser superior a 3 meses");
        }

    }

    @Nested
    public class ValidacaoQuantidadePessoas {

        @Test
        @DisplayName("Deve lançar exceção para quantidade de pessoas nula")
        public void deveLancarExcecaoParaQuantidadePessoasNula() {
            Integer quantidadePessoasNula = null;
            assertThatThrownBy(() -> new Reserva(status, agendamento, quantidadePessoasNula, restaurante, mesa, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantidade de pessoas inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para quantidade de pesosas inferior ao minimo permitido")
        public void deveLancarExcecaoParaQuantidadePessoasInferiorAoMinimoPermitido() {
            Integer quantidadePessoasInferiorAoMinimoPermitido = 0;
            assertThatThrownBy(() -> new Reserva(status, agendamento, quantidadePessoasInferiorAoMinimoPermitido, restaurante, mesa, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantidade de pessoas inferior ao permitido");
        }

        @Test
        @DisplayName("Deve lançar exceção para quantidade de pesosas superior ao maximo permitido")
        public void deveLancarExcecaoParaQuantidadePessoasSuperiorAoMaximoPermitido() {
            Integer quantidadePessoasSuperiorAoMaximoPermitido = 101;
            assertThatThrownBy(() -> new Reserva(status, agendamento, quantidadePessoasSuperiorAoMaximoPermitido, restaurante, mesa, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("quantidade de pessoas superior ao permitido");
        }

    }

    @Nested
    public class ValidacaoRestaurante {

        @Test
        @DisplayName("Deve lançar exceção para restaurante nulo")
        public void deveLancarExcecaoParaRestauranteNulo() {
            Restaurante restauranteNulo = null;
            assertThatThrownBy(() -> new Reserva(status, agendamento, quantidadePessoas, restauranteNulo, mesa, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("restaurante inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para restaurante com id nulo")
        public void deveLancarExcecaoParaRestauranteComIdNulo() {
            Restaurante restauranteComIdNulo = new Restaurante();
            assertThatThrownBy(() -> new Reserva(status, agendamento, quantidadePessoas, restauranteComIdNulo, mesa, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("restaurante inválido");
        }

    }

    @Nested
    public class ValidacaoMesa {

        @Test
        @DisplayName("Deve lançar exceção para mesa nula")
        public void deveLancarExcecaoParaMesaNula() {
            Mesa mesaNula = null;
            assertThatThrownBy(() -> new Reserva(status, agendamento, quantidadePessoas, restaurante, mesaNula, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("mesa inválida");
        }

        @Test
        @DisplayName("Deve lançar exceção para mesa com id nulo")
        public void deveLancarExcecaoParaMesaComIdNulo() {
            Mesa mesaComIdNulo = new Mesa();
            assertThatThrownBy(() -> new Reserva(status, agendamento, quantidadePessoas, restaurante, mesaComIdNulo, cliente))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("mesa inválida");
        }

    }

    @Nested
    public class ValidacaoCliente {

        @Test
        @DisplayName("Deve lançar exceção para cliente nulo")
        public void deveLancarExcecaoParaClienteNulo() {
            Cliente clienteNulo = null;
            assertThatThrownBy(() -> new Reserva(status, agendamento, quantidadePessoas, restaurante, mesa, clienteNulo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cliente inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção para cliente com id nulo")
        public void deveLancarExcecaoParaClienteComIdNulo() {
            Cliente clienteComIdNulo = new Cliente();
            assertThatThrownBy(() -> new Reserva(status, agendamento, quantidadePessoas, restaurante, mesa, clienteComIdNulo))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("cliente inválido");
        }

    }

}
