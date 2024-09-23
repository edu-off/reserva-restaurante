package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.AvaliacaoDTO;
import br.com.reserva.application.exceptions.AvaliacaoException;
import br.com.reserva.application.exceptions.EntityNotFoundException;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class AvaliaReservaControllerIT {

    @Autowired
    private AvaliaReservaController avaliaReservaController;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    private ReservaModel reservaModel;

    @BeforeEach
    public void setup() {
        clienteRepository.deleteAll();
        restauranteRepository.deleteAll();
        reservaRepository.deleteAll();
        ClienteModel clienteModel = new ClienteModel("teste@teste.com.br", "nome", 1, 1L, null);
        RestauranteModel restauranteModel = new RestauranteModel(null, "nome", "culinaria", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
        MesaModel mesaModel = new MesaModel(null, 1, StatusMesa.DISPONIVEL, restauranteModel, null);
        reservaModel = new ReservaModel(null, StatusReserva.ATIVA, LocalDateTime.now(), 1, restauranteModel, clienteModel, mesaModel, null);
        clienteRepository.save(clienteModel);
        restauranteRepository.save(restauranteModel);
        mesaRepository.save(mesaModel);
        reservaRepository.save(reservaModel);
    }

    @Nested
    public class ValidacaoAvaliacaoReserva {

        @Test
        @DisplayName("Deve lancar excecao na verificacao de existencia da reserva")
        public void deveLancarExcecaoNaVerificacaoDaExistenciaDaReserva() {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "titulo", 1, "comentario");
            assertThatThrownBy(() -> avaliaReservaController.execute(reservaModel.getId() + 1, avaliacaoDTO))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("reserva não encontrada");
        }

        @Test
        @DisplayName("Deve lancar excecao na validacao da avaliacao")
        public void deveLancarExcecaoNaValidacaoDaAvaliacao() {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "", 1, "comentario");
            assertThatThrownBy(() -> avaliaReservaController.execute(reservaModel.getId(), avaliacaoDTO))
                    .isInstanceOf(AvaliacaoException.class)
                    .hasMessage("erro ao validar dados da avaliaçâo: título inválido");
        }

        @Test
        @DisplayName("Deve cadastrar cliente corretamente")
        public void deveCadastrarClienteCorretamente() {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO(null, "titulo", 1, "comentario");
            avaliaReservaController.execute(reservaModel.getId(), avaliacaoDTO);
        }

    }

}
