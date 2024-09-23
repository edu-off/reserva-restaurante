package br.com.reserva.infrastructure.database.repositories;

import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import br.com.reserva.infrastructure.database.models.*;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class ReservaRepositoryIT {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Nested
    public class ValidacaoReservaRepository {

        @Test
        @DisplayName("Deve incluir para recuperar um documento na collection reserva")
        public void deveIncluirParaRecuperarUmDocumentoNaCollectionReserva() {
            ReservaModel reservaModel = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, null, null, null, null);
            ReservaModel reservaCreated = reservaRepository.save(reservaModel);
            Optional<ReservaModel> reservaRetrieved = reservaRepository.findById(reservaCreated.getId());
            Assertions.assertThat(reservaRetrieved.isPresent()).isTrue();
            Assertions.assertThat(reservaRetrieved.get()).isInstanceOf(ReservaModel.class).isNotNull();
            Assertions.assertThat(reservaRetrieved.get().getId()).isNotNull();
            Assertions.assertThat(reservaRetrieved.get().getStatus()).isEqualTo(reservaModel.getStatus());
            Assertions.assertThat(reservaRetrieved.get().getAgendamento()).isInstanceOf(LocalDateTime.class).isNotNull();
            Assertions.assertThat(reservaRetrieved.get().getQuantidadePessoas()).isEqualTo(reservaModel.getQuantidadePessoas());
            Assertions.assertThat(reservaRetrieved.get().getRestaurante()).isNull();
            Assertions.assertThat(reservaRetrieved.get().getCliente()).isNull();
            Assertions.assertThat(reservaRetrieved.get().getAvaliacao()).isNull();
        }


        @Test
        @DisplayName("Deve incluir para recuperar um documento na collection reserva com restaurante")
        public void deveIncluirParaRecuperarUmDocumentoNaCollectionReservaComRestaurante() {
            RestauranteModel restauranteModel = new RestauranteModel(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), null, null, null, null, null);
            RestauranteModel restauranteCreated = restauranteRepository.save(restauranteModel);
            ReservaModel reservaModel = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, restauranteCreated, null, null, null);
            ReservaModel reservaCreated = reservaRepository.save(reservaModel);
            Optional<ReservaModel> reservaRetrieved = reservaRepository.findById(reservaCreated.getId());
            Assertions.assertThat(reservaRetrieved.isPresent()).isTrue();
            Assertions.assertThat(reservaRetrieved.get()).isInstanceOf(ReservaModel.class).isNotNull();
            Assertions.assertThat(reservaRetrieved.get().getRestaurante()).isInstanceOf(RestauranteModel.class).isNotNull();
        }

        @Test
        @DisplayName("Deve incluir para recuperar um documento na collection reserva com cliente")
        public void deveIncluirParaRecuperarUmDocumentoNaCollectionReservaComCliente() {
            ClienteModel clienteModel = new ClienteModel("teste@teste.com.br", "nome", null, null, null);
            ClienteModel clienteCreated = clienteRepository.save(clienteModel);
            ReservaModel reservaModel = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, null, clienteCreated, null, null);
            ReservaModel reservaCreated = reservaRepository.save(reservaModel);
            Optional<ReservaModel> reservaRetrieved = reservaRepository.findById(reservaCreated.getId());
            Assertions.assertThat(reservaRetrieved.isPresent()).isTrue();
            Assertions.assertThat(reservaRetrieved.get()).isInstanceOf(ReservaModel.class).isNotNull();
            Assertions.assertThat(reservaRetrieved.get().getCliente()).isInstanceOf(ClienteModel.class).isNotNull();
        }

        @Test
        @DisplayName("Deve incluir para recuperar um documento na collection reserva com avaliacao")
        public void deveIncluirParaRecuperarUmDocumentoNaCollectionReservaComAvaliacao() {
            AvaliacaoModel avaliacaoModel = new AvaliacaoModel(null, "titulo", "comentario", 1, null);
            AvaliacaoModel avaliacaoCreated = avaliacaoRepository.save(avaliacaoModel);
            ReservaModel reservaModel = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, null, null,  null, avaliacaoCreated);
            ReservaModel reservaCreated = reservaRepository.save(reservaModel);
            Optional<ReservaModel> reservaRetrieved = reservaRepository.findById(reservaCreated.getId());
            Assertions.assertThat(reservaRetrieved.isPresent()).isTrue();
            Assertions.assertThat(reservaRetrieved.get()).isInstanceOf(ReservaModel.class).isNotNull();
            Assertions.assertThat(reservaRetrieved.get().getAvaliacao()).isInstanceOf(AvaliacaoModel.class).isNotNull();
        }

    }

    @Nested
    public class ValidacaoBuscasRepository {

        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        private ClienteModel clienteCreated;
        private RestauranteModel restauranteCreated;

        @BeforeEach
        public void setup() {
            restauranteRepository.deleteAll();
            clienteRepository.deleteAll();
            reservaRepository.deleteAll();
            saveReservas();
        }

        @Test
        @DisplayName("Deve buscar corretamente pelo id do restaurante")
        public void deveBuscarCorretamentePeloIdDoRestaurante() {
            Page<ReservaModel> models = reservaRepository.findByRestauranteId(restauranteCreated.getId(), PageRequest.of(0, 10));
            Assertions.assertThat(models).isInstanceOf(Page.class).isNotEmpty().hasSize(4);
        }

        @Test
        @DisplayName("Deve buscar corretamente pelo id do restaurante mais periodo")
        public void deveBuscarCorretamentePeloIdDoRestauranteMaisPeriodo() {
            LocalDateTime inicio = LocalDateTime.parse("2024-09-01 01:00", formatter);
            LocalDateTime fim = LocalDateTime.parse("2024-09-01 23:00", formatter);
            Page<ReservaModel> models = reservaRepository.findByRestauranteIdPeriodo(restauranteCreated.getId(), inicio, fim, PageRequest.of(0, 10));
            Assertions.assertThat(models).isInstanceOf(Page.class).isNotEmpty().hasSize(1);
        }

        @Test
        @DisplayName("Deve buscar corretamente pelo id do cliente")
        public void deveBuscarCorretamentePeloIdDoCliente() {
            Page<ReservaModel> models = reservaRepository.findByClienteId(clienteCreated.getEmail(), PageRequest.of(0, 10));
            Assertions.assertThat(models).isInstanceOf(Page.class).isNotEmpty().hasSize(4);
        }

        @Test
        @DisplayName("Deve buscar corretamente pelo id do cliente mais periodo")
        public void deveBuscarCorretamentePeloIdDoClienteMaisPeriodo() {
            LocalDateTime inicio = LocalDateTime.parse("2024-09-01 01:00", formatter);
            LocalDateTime fim = LocalDateTime.parse("2024-09-01 23:00", formatter);
            Page<ReservaModel> models = reservaRepository.findByClienteIdPeriodo(clienteCreated.getEmail(), inicio, fim, PageRequest.of(0, 10));
            Assertions.assertThat(models).isInstanceOf(Page.class).isNotEmpty().hasSize(1);
        }

        private void saveReservas() {
            ClienteModel clienteModel = new ClienteModel("teste@teste.com.br", "nome", null, null, null);
            RestauranteModel restauranteModel = new RestauranteModel(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), null, null, null, null, null);
            MesaModel mesaModel = new MesaModel(null, 1, StatusMesa.DISPONIVEL, restauranteModel, null);
            clienteCreated = clienteRepository.save(clienteModel);
            restauranteCreated = restauranteRepository.save(restauranteModel);
            MesaModel mesaCreated = mesaRepository.save(mesaModel);
            ReservaModel reservaModel1 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-09-01 21:00", formatter), 1, restauranteCreated, clienteCreated, mesaCreated, null);
            ReservaModel reservaModel2 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-08-01 20:00", formatter), 1, restauranteCreated, clienteCreated, mesaCreated, null);
            ReservaModel reservaModel3 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, restauranteCreated, clienteCreated, mesaCreated, null);
            ReservaModel reservaModel4 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, restauranteCreated, clienteCreated, mesaCreated, null);
            ReservaModel reservaModel5 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, null, null, null, null);
            ReservaModel reservaModel6 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, null, null, null, null);
            List<ReservaModel> reservas = List.of(reservaModel1, reservaModel2, reservaModel3, reservaModel4, reservaModel5, reservaModel6);
            reservaRepository.saveAll(reservas);
        }

    }

}
