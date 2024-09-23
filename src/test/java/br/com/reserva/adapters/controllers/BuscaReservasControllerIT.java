package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.domain.enums.StatusReserva;
import br.com.reserva.infrastructure.database.models.ClienteModel;
import br.com.reserva.infrastructure.database.models.MesaModel;
import br.com.reserva.infrastructure.database.models.ReservaModel;
import br.com.reserva.infrastructure.database.models.RestauranteModel;
import br.com.reserva.infrastructure.database.repositories.ClienteRepository;
import br.com.reserva.infrastructure.database.repositories.MesaRepository;
import br.com.reserva.infrastructure.database.repositories.ReservaRepository;
import br.com.reserva.infrastructure.database.repositories.RestauranteRepository;
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
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class BuscaReservasControllerIT {

    @Autowired
    private BuscaReservasController buscaReservasController;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private ClienteModel clienteCreated;
    private RestauranteModel restauranteCreated;

    @Nested
    public class ValidacaoDeBuscas {

        @BeforeEach
        public void setup() {
            reservaRepository.deleteAll();
            restauranteRepository.deleteAll();
            clienteRepository.deleteAll();
            saveReservas();
        }

        @Test
        @DisplayName("Deve buscar reservas por cliente")
        public void deveBuscarReservasPorCliente() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ReservaDTO> pageReservasDTOReturned = buscaReservasController.porCliente(clienteCreated.getEmail(), pageable);
            Assertions.assertThat(pageReservasDTOReturned).isInstanceOf(Page.class).isNotEmpty().hasSize(4);
        }

        @Test
        @DisplayName("Deve buscar reservas por restaurante")
        public void deveBuscarReservasPorRestaurante() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<ReservaDTO> pageReservasDTOReturned = buscaReservasController.porRestaurante(restauranteCreated.getId(), pageable);
            Assertions.assertThat(pageReservasDTOReturned).isInstanceOf(Page.class).isNotEmpty().hasSize(4 );
        }

        @Test
        @DisplayName("Deve buscar reservas por cliente e periodo")
        public void deveBuscarReservasPorClienteEPeriodo() {
            LocalDateTime inicio = LocalDateTime.parse("2024-09-01 01:00", formatter);
            LocalDateTime fim = LocalDateTime.parse("2024-09-01 23:00", formatter);
            Pageable pageable = PageRequest.of(0, 10);
            Page<ReservaDTO> pageReservasDTOReturned = buscaReservasController.porClientePeriodo(clienteCreated.getEmail(), inicio, fim, pageable);
            Assertions.assertThat(pageReservasDTOReturned).isInstanceOf(Page.class).isNotEmpty().hasSize(1);
        }

        @Test
        @DisplayName("Deve buscar reservas por restaurante e periodo")
        public void deveBuscarReservasPorRestauranteEPeriodo() {
            LocalDateTime inicio = LocalDateTime.parse("2024-09-01 01:00", formatter);
            LocalDateTime fim = LocalDateTime.parse("2024-09-01 23:00", formatter);
            Pageable pageable = PageRequest.of(0, 10);
            Page<ReservaDTO> pageReservasDTOReturned = buscaReservasController.porRestaurantePeriodo(restauranteCreated.getId(), inicio, fim, pageable);
            Assertions.assertThat(pageReservasDTOReturned).isInstanceOf(Page.class).isNotEmpty().hasSize(1);
        }

        private void saveReservas() {
            ClienteModel clienteModel = new ClienteModel("teste@teste.com.br", "nome", null, null, null);
            RestauranteModel restauranteModel = new RestauranteModel(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), null, null, null, null, null);
            MesaModel mesaModel = new MesaModel(null, 1, StatusMesa.DISPONIVEL, restauranteModel, null);
            clienteCreated = clienteRepository.save(clienteModel);
            restauranteCreated = restauranteRepository.save(restauranteModel);
            MesaModel mesaModelCreated = mesaRepository.save(mesaModel);

            ReservaModel reservaModel1 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-09-01 21:00", formatter), 1, restauranteCreated, clienteCreated, mesaModelCreated, null);
            ReservaModel reservaModel2 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.parse("2024-08-01 20:00", formatter), 1, restauranteCreated, clienteCreated, mesaModelCreated, null);
            ReservaModel reservaModel3 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, restauranteCreated, clienteCreated, mesaModelCreated, null);
            ReservaModel reservaModel4 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, restauranteCreated, clienteCreated, mesaModelCreated, null);
            ReservaModel reservaModel5 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, null, null, null, null);
            ReservaModel reservaModel6 = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, null, null, null, null);
            List<ReservaModel> reservas = List.of(reservaModel1, reservaModel2, reservaModel3, reservaModel4, reservaModel5, reservaModel6);
            reservaRepository.saveAll(reservas);
        }

    }

}
