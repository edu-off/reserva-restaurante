package br.com.reserva.adapters.controllers;

import br.com.reserva.application.dto.RestauranteDTO;
import br.com.reserva.infrastructure.database.models.RestauranteModel;
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

import java.time.LocalTime;
import java.util.List;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class BuscaRestaurantesControllerIT {

    @Autowired
    private BuscaRestaurantesController buscaRestaurantesController;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Nested
    public class ValidacaoDeBuscas {

        @BeforeEach
        public void setup() {
            restauranteRepository.deleteAll();
            saveRestaurantes();
        }

        @Test
        @DisplayName("Deve buscar restaurantes por nome encontrado")
        public void deveBuscarRestaurantesPorNomeEncontrado() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<RestauranteDTO> pageRestaurantesDTOReturned = buscaRestaurantesController.porNome("restaurante", pageable);
            Assertions.assertThat(pageRestaurantesDTOReturned).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        @Test
        @DisplayName("Deve buscar restaurantes por culinaria encontrada")
        public void deveBuscarRestaurantesPorCulinariaEncontrada() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<RestauranteDTO> pageRestaurantesDTOReturned = buscaRestaurantesController.porCulinaria("arabe", pageable);
            Assertions.assertThat(pageRestaurantesDTOReturned).isInstanceOf(Page.class).isNotEmpty().hasSize(2);
        }

        private void saveRestaurantes() {
            RestauranteModel restauranteModel1 = new RestauranteModel(null, "restaurante 1", "culinaria japonesa", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), null, null, null, null, null);
            RestauranteModel restauranteModel2 = new RestauranteModel(null, "restaurante 2", "culinaria arabe", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), null, null, null, null, null);
            RestauranteModel restauranteModel3 = new RestauranteModel(null, "teste", "culinaria arabe", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), null, null, null, null, null);
            RestauranteModel restauranteModel4 = new RestauranteModel(null, "nome 1", "culinaria mexicana", 1, LocalTime.parse("12:00"), LocalTime.parse("23:00"), null, null, null, null, null);
            restauranteRepository.saveAll(List.of(restauranteModel1, restauranteModel2, restauranteModel3, restauranteModel4));
        }

    }

}
