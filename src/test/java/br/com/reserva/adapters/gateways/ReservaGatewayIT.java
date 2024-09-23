package br.com.reserva.adapters.gateways;

import br.com.reserva.application.gateways.ClienteGateway;
import br.com.reserva.application.gateways.MesaGateway;
import br.com.reserva.application.gateways.ReservaGateway;
import br.com.reserva.application.gateways.RestauranteGateway;
import br.com.reserva.domain.entities.Cliente;
import br.com.reserva.domain.entities.Mesa;
import br.com.reserva.domain.entities.Reserva;
import br.com.reserva.domain.entities.Restaurante;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import br.com.reserva.domain.objectsvalue.Endereco;
import br.com.reserva.infrastructure.database.repositories.ClienteRepository;
import br.com.reserva.infrastructure.database.repositories.ReservaRepository;
import br.com.reserva.infrastructure.database.repositories.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class ReservaGatewayIT {

    @Autowired
    private ReservaGateway reservaGateway;

    @Autowired
    private RestauranteGateway restauranteGateway;

    @Autowired
    private MesaGateway mesaGateway;

    @Autowired
    private ClienteGateway clienteGateway;

    @Nested
    public class ValidacaoMesaGateway {

        @Test
        @DisplayName("Deve salvar reserva")
        public void deveSalvarReserva() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setEndereco(null);
            Restaurante restauranteCreated = restauranteGateway.salvaRestaurante(restaurante);
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Cliente clienteCreated = clienteGateway.salvaCliente(cliente);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesaCreated = mesaGateway.salvaMesa(mesa);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restauranteCreated, mesaCreated, clienteCreated);
            Reserva reservaCreated = reservaGateway.salvaReserva(reserva);
            assertThat(reservaCreated).isInstanceOf(Reserva.class).isNotNull();
            assertThat(reservaCreated.getId()).isNotNull();
            assertThat(reservaCreated.getStatus()).isEqualTo(reserva.getStatus());
            assertThat(reservaCreated.getAgendamento()).isEqualTo(reserva.getAgendamento());
            assertThat(reservaCreated.getQuantidadePessoas()).isEqualTo(reserva.getQuantidadePessoas());
            assertThat(reservaCreated.getRestaurante()).isInstanceOf(Restaurante.class).isNotNull();
            assertThat(reservaCreated.getCliente()).isInstanceOf(Cliente.class).isNotNull();
            assertThat(reservaCreated.getAvaliacao()).isNull();
        }

        @Test
        @DisplayName("Deve atualizar reserva existente")
        public void deveAtualizarReservaExistente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setEndereco(null);
            Restaurante restauranteCreated = restauranteGateway.salvaRestaurante(restaurante);
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Cliente clienteCreated = clienteGateway.salvaCliente(cliente);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesaCreated = mesaGateway.salvaMesa(mesa);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restauranteCreated, mesaCreated, clienteCreated);
            Reserva reservaCreated = reservaGateway.salvaReserva(reserva);
            reservaCreated.setStatus(StatusReserva.FINALIZADA);
            Reserva reservaUpdated = reservaGateway.atualizaReserva(reservaCreated.getId(), reservaCreated);
            assertThat(reservaUpdated).isInstanceOf(Reserva.class).isNotNull();
            assertThat(reservaUpdated.getStatus()).isNotEqualTo(reserva.getStatus());
            assertThat(reservaUpdated.getStatus()).isEqualTo(StatusReserva.FINALIZADA);
        }

        @Test
        @DisplayName("Deve atualizar reserva inexistente")
        public void deveAtualizarReservaInexistente() {
            Restaurante restaurante = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante.setEndereco(null);
            Restaurante restauranteCreated = restauranteGateway.salvaRestaurante(restaurante);
            Cliente cliente = new Cliente("teste@teste.com.br", "nome", 1, 1L);
            Cliente clienteCreated = clienteGateway.salvaCliente(cliente);
            Mesa mesa = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesaCreated = mesaGateway.salvaMesa(mesa);
            Reserva reserva = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restauranteCreated, mesaCreated, clienteCreated);
            Reserva reservaUpdated = reservaGateway.atualizaReserva(0L, reserva);
            assertThat(reservaUpdated).isNull();
        }

    }

    @Nested
    public class ValidacaoBuscasMesaGateway {

        @Autowired
        private ReservaRepository reservaRepository;

        @Autowired
        private RestauranteRepository restauranteRepository;

        @Autowired
        private ClienteRepository clienteRepository;

        private Long restauranteId;

        private Long reservaId;

        @BeforeEach
        public void setup() {
            reservaRepository.deleteAll();
            restauranteRepository.deleteAll();
            clienteRepository.deleteAll();
            salvaRegistros();
        }

        @Test
        @DisplayName("Deve buscar reserva por id")
        public void deveBuscarReservasPorId() {
            salvaRegistros();
            Reserva reservaRetrieved = reservaGateway.buscaReservaPorId(reservaId);
            assertThat(reservaRetrieved).isInstanceOf(Reserva.class).isNotNull();
        }

        @Test
        @DisplayName("Deve buscar reserva por id inexistente")
        public void deveBuscarReservasPorIdInexsitente() {
            Reserva reservaRetrieved = reservaGateway.buscaReservaPorId(0L);
            assertThat(reservaRetrieved).isNull();
        }

        @Test
        @DisplayName("Deve buscar reservas por restaurante")
        public void deveBuscarReservasPorRestaurante() {
            Page<Reserva> reservas = reservaGateway.buscaReservasPorRestaurante(restauranteId, PageRequest.of(0, 10));
            assertThat(reservas).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve buscar reservas por restaurante e por periodo")
        public void deveBuscarReservasPorRestauranteEPorPeriodo() {
            LocalDateTime inicio = LocalDateTime.now().minusDays(1);
            LocalDateTime fim = LocalDateTime.now().plusDays(1);
            Page<Reserva> reservas = reservaGateway.buscaReservasPorRestaurantePeriodo(restauranteId, inicio, fim, PageRequest.of(0, 10));
            assertThat(reservas).isInstanceOf(Page.class).isNotEmpty().hasSize(1);
        }

        @Test
        @DisplayName("Deve buscar reservas por cliente")
        public void deveBuscarReservasPorCliente() {
            Page<Reserva> reservas = reservaGateway.buscaReservasPorCliente("teste1@teste1.com.br", PageRequest.of(0, 10));
            assertThat(reservas).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve buscar reservas por cliente e por periodo")
        public void deveBuscarReservasPorClienteEPorPeriodo() {
            LocalDateTime inicio = LocalDateTime.now().minusDays(1);
            LocalDateTime fim = LocalDateTime.now().plusDays(1);
            Page<Reserva> reservas = reservaGateway.buscaReservasPorClientePeriodo("teste1@teste1.com.br", inicio, fim, PageRequest.of(0, 10));
            assertThat(reservas).isInstanceOf(Page.class).isNotEmpty().hasSize(1);
        }

        private void salvaRegistros() {
            Restaurante restaurante1 = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            Restaurante restaurante2 = new Restaurante("nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, new Endereco());
            restaurante1.setEndereco(null);
            restaurante2.setEndereco(null);
            Restaurante restauranteCreated1 = restauranteGateway.salvaRestaurante(restaurante1);
            Restaurante restauranteCreated2 = restauranteGateway.salvaRestaurante(restaurante2);
            restauranteId = restauranteCreated1.getId();
            Cliente cliente1 = new Cliente("teste1@teste1.com.br", "nome", 1, 1L);
            Cliente cliente2 = new Cliente("teste2@teste2.com.br", "nome", 1, 1L);
            Cliente clienteCreated1 = clienteGateway.salvaCliente(cliente1);
            Cliente clienteCreated2 = clienteGateway.salvaCliente(cliente2);
            Mesa mesa1 = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesa2 = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesa3 = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesa4 = new Mesa(1, StatusMesa.DISPONIVEL);
            Mesa mesaCreated1 = mesaGateway.salvaMesa(mesa1);
            Mesa mesaCreated2 = mesaGateway.salvaMesa(mesa2);
            Mesa mesaCreated3 = mesaGateway.salvaMesa(mesa3);
            Mesa mesaCreated4 = mesaGateway.salvaMesa(mesa4);
            Reserva reserva1 = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restauranteCreated1, mesaCreated1, clienteCreated1);
            Reserva reserva2 = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusDays(7), 2, restauranteCreated1, mesaCreated2, clienteCreated1);
            Reserva reserva3 = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restauranteCreated2, mesaCreated3, clienteCreated2);
            Reserva reserva4 = new Reserva(StatusReserva.ATIVA, LocalDateTime.now().plusDays(7), 2, restauranteCreated2, mesaCreated4, clienteCreated2);
            Reserva reservaCreated1 = reservaGateway.salvaReserva(reserva1);
            reservaGateway.salvaReserva(reserva2);
            reservaGateway.salvaReserva(reserva3);
            reservaGateway.salvaReserva(reserva4);
            reservaId = reservaCreated1.getId();
        }

    }

}
