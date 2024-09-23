package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.ReservaDTO;
import br.com.reserva.application.exceptions.ReservaException;
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
public class GerenciaReservaControllerIT {

    @Autowired
    private GerenciaReservaController gerenciaReservaController;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    private ReservaModel reservaModel;

    @BeforeEach
    public void setup() {
        clienteRepository.deleteAll();
        restauranteRepository.deleteAll();
        reservaRepository.deleteAll();
        ClienteModel clienteModel = new ClienteModel("teste@teste.com.br", "nome", null, null, null);
        RestauranteModel restauranteModel = new RestauranteModel(null, "nome", "culinaria",
                1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), null, null, null, null, null);
        MesaModel mesaModel = new MesaModel(null, 1, StatusMesa.DISPONIVEL, restauranteModel, null);
        ClienteModel clienteCreated = clienteRepository.save(clienteModel);
        RestauranteModel restauranteCreated = restauranteRepository.save(restauranteModel);
        MesaModel mesaModelCreated = mesaRepository.save(mesaModel);
        reservaModel = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now().plusHours(7), 2, restauranteCreated, clienteCreated, mesaModelCreated, null);
        reservaRepository.save(reservaModel);
    }

    @Nested
    public class ValidacaoGerenciamentoReserva {

        @Test
        @DisplayName("Deve lancar excecao para reserva nao encontrada")
        public void deveLancarExcecaoParaReservaNaoEncontrada() {
            ReservaDTO reservaDTO = new ReservaDTO(1L, "CANCELADA", LocalDateTime.now().plusHours(7),
                    1, null, null, null);
            assertThatThrownBy(() -> gerenciaReservaController.execute(reservaModel.getId() + 1, reservaDTO))
                    .isInstanceOf(ReservaException.class)
                    .hasMessage("reserva não encontrada");
        }

        @Test
        @DisplayName("Deve ativar reserva corretamente")
        public void deveAtivarReservaCorretamente() {
            reservaModel.setStatus(StatusReserva.CANCELADA);
            ReservaModel reservaModelUpdated = reservaRepository.save(reservaModel);
            String statusOld = reservaModelUpdated.getStatus().toString();
            ReservaDTO reservaDTO = new ReservaDTO(1L, "ATIVA", LocalDateTime.now().plusHours(7),
                    1, null, null, null);
            ReservaDTO reservaDTOUpdated = gerenciaReservaController.execute(reservaModel.getId(), reservaDTO);
            assertThat(reservaDTOUpdated).isInstanceOf(ReservaDTO.class).isNotNull();
            assertThat(reservaDTOUpdated.getStatus()).isEqualTo(reservaDTO.getStatus()).isNotEqualTo(statusOld);
        }


        @Test
        @DisplayName("Deve cancelar reserva corretamente")
        public void deveCancelarReservaCorretamente() {
            String statusOld = reservaModel.getStatus().toString();
            ReservaDTO reservaDTO = new ReservaDTO(1L, "CANCELADA", LocalDateTime.now().plusHours(7),
                    1, null, null, null);
            ReservaDTO reservaDTOUpdated = gerenciaReservaController.execute(reservaModel.getId(), reservaDTO);
            assertThat(reservaDTOUpdated).isInstanceOf(ReservaDTO.class).isNotNull();
            assertThat(reservaDTOUpdated.getStatus()).isEqualTo(reservaDTO.getStatus()).isNotEqualTo(statusOld);
        }

    }

}
