package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.ClienteDTO;
import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.application.exceptions.ClienteException;
import br.com.reserva.application.exceptions.EntityNotFoundException;
import br.com.reserva.application.exceptions.ReservaException;
import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.domain.enums.StatusMesa;
import br.com.reserva.infrastructure.database.models.ClienteModel;
import br.com.reserva.infrastructure.database.models.MesaModel;
import br.com.reserva.infrastructure.database.models.RestauranteModel;
import br.com.reserva.infrastructure.database.repositories.ClienteRepository;
import br.com.reserva.infrastructure.database.repositories.MesaRepository;
import br.com.reserva.infrastructure.database.repositories.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class EfetuaReservaControllerIT {

    @Autowired
    private EfetuaReservaController efetuaReservaController;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    private RestauranteModel restauranteModel;

    @BeforeEach
    public void setup() {
        clienteRepository.deleteAll();
        restauranteRepository.deleteAll();
        mesaRepository.deleteAll();
        ClienteModel clienteModel = new ClienteModel("teste@teste.com.br", "nome", 1, 1L, null);
        restauranteModel = new RestauranteModel(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
        MesaModel mesaModel = new MesaModel(null, 1, StatusMesa.DISPONIVEL, restauranteModel, null);
        clienteRepository.save(clienteModel);
        restauranteRepository.save(restauranteModel);
        mesaRepository.save(mesaModel);
    }

    @Nested
    public class ValidacaoCriacaoReserva {

        @Test
        @DisplayName("deve lancar excecao para cliente inexistente")
        public void deveLancarExcecaoParaClienteInexistente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste1@teste1.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(restauranteModel.getId(), "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);
            assertThatThrownBy(() -> efetuaReservaController.execute(reservaDTO))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("cliente não encontrado");
        }

        @Test
        @DisplayName("deve lancar excecao para restaurante inexistente")
        public void deveLancarExcecaoParaRestauranteInexistente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(restauranteModel.getId() + 1, "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);
            assertThatThrownBy(() -> efetuaReservaController.execute(reservaDTO))
                    .isInstanceOf(RestauranteException.class)
                    .hasMessage("restaurante não encontrado");
        }

        @Test
        @DisplayName("deve lancar excecao para reserva invalida")
        public void deveLancarExcecaoParaReservaInvalida() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(restauranteModel.getId(), "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now(), 1, restauranteDTO, clienteDTO, null);
            assertThatThrownBy(() -> efetuaReservaController.execute(reservaDTO))
                    .isInstanceOf(ReservaException.class)
                    .hasMessage("erro ao registrar reserva: data e hora de agendamento não deve possuir intervalo inferior 6 horas do instante da reserva");
        }

        @Test
        @DisplayName("deve lancar excecao para restaurante com ocupacao maxima")
        public void deveLancarExcecaoParaRestauranteComOcupacaoMaxima() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(restauranteModel.getId(), "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 2, restauranteDTO, clienteDTO, null);
            assertThatThrownBy(() -> efetuaReservaController.execute(reservaDTO))
                    .isInstanceOf(ReservaException.class)
                    .hasMessage("restaurante não possui mesa com a quantidade de pessoas solicitada");
        }

        @Test
        @DisplayName("deve efetuar reserva corretamente")
        public void deveEfetuarReservaCorretamente() {
            ClienteDTO clienteDTO = new ClienteDTO("teste@teste.com.br", "nome", 1, 1L);
            RestauranteDTO restauranteDTO = new RestauranteDTO(restauranteModel.getId(), "nome", "culinaria", 1, LocalTime.parse("23:00"), LocalTime.parse("23:00"), 1, 1L, null);
            ReservaDTO reservaDTO = new ReservaDTO(null, "ATIVA", LocalDateTime.now().plusHours(7), 1, restauranteDTO, clienteDTO, null);
            ReservaDTO reservaDTOCreated = efetuaReservaController.execute(reservaDTO);
            assertThat(reservaDTOCreated).isInstanceOf(ReservaDTO.class).isNotNull();
        }

    }

}
