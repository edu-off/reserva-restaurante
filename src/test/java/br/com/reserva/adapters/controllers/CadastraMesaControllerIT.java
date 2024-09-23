package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.MesaDTO;
import br.com.reserva.application.exceptions.MesaException;
import br.com.reserva.application.exceptions.RestauranteException;
import br.com.reserva.infrastructure.database.models.RestauranteModel;
import br.com.reserva.infrastructure.database.repositories.RestauranteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class CadastraMesaControllerIT {

    @Autowired
    private CadastraMesaController cadastraMesaController;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Nested
    public class CriacaoMesa {

        @Test
        @DisplayName("Deve lancar excecao na validacao de mesa")
        public void deveLancarExcecaoNaValidacaoDeMesa() {
            MesaDTO mesaDTO = new MesaDTO(null, 0);
            assertThatThrownBy(() -> cadastraMesaController.execute(1L, mesaDTO))
                    .isInstanceOf(MesaException.class)
                    .hasMessage("erro ao validar mesa: capacidade inferior ao permitido");
        }

        @Test
        @DisplayName("Deve lancar excecao na verificacao de existencia de restaurante")
        public void deveLancarExcecaoNaVerificacaoDeExistenciaDeRestaurante() {
            MesaDTO mesaDTO = new MesaDTO(null, 1);
            assertThatThrownBy(() -> cadastraMesaController.execute(1L, mesaDTO))
                    .isInstanceOf(RestauranteException.class)
                    .hasMessage("restaurante não encontrado");
        }

        @Test
        @DisplayName("Deve criar mesa corretamente")
        public void deveCriarMesaCorretamente() {
            RestauranteModel restauranteModel = new RestauranteModel(null, "nome", "culinaria",
                    1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), 1, 1L, null, null, null);
            RestauranteModel restauranteModelCreated = restauranteRepository.save(restauranteModel);
            MesaDTO mesaDTO = new MesaDTO(null, 1);
            MesaDTO mesaDTOCreated = cadastraMesaController.execute(restauranteModelCreated.getId(), mesaDTO);
            assertThat(mesaDTOCreated).isInstanceOf(MesaDTO.class).isNotNull();
            assertThat(mesaDTOCreated.getId()).isNotNull().isNotZero();
        }

    }

}
